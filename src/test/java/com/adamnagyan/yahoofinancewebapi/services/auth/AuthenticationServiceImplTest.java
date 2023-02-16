package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.AuthenticationRequestMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.ConfirmationTokenExpiredException;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserIsAlreadyEnabledException;
import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;
import com.adamnagyan.yahoofinancewebapi.model.user.Role;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.repositories.user.UserRepository;
import com.adamnagyan.yahoofinancewebapi.services.email.EmailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceImplTest {

	AuthenticationServiceImpl authenticationService;

	AuthenticationServiceImpl mockedAuthenticationService;

	AuthenticationRequestMapper authenticationMapper;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	UserRepository userRepository;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	ConfirmationTokenService confirmationTokenService;

	@Mock
	EmailService emailService;

	@Mock
	JwtService jwtService;
	static String CONFIRM_URL = "https://test";
	static Integer TOKEN_EXPIRY = 15;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		authenticationMapper = AuthenticationRequestMapper.INSTANCE;
		authenticationService = new AuthenticationServiceImpl(authenticationMapper, authenticationManager,
				userRepository, passwordEncoder, confirmationTokenService, emailService, jwtService);
		ReflectionTestUtils.setField(authenticationService, "confirmUrl", CONFIRM_URL);
		ReflectionTestUtils.setField(authenticationService, "confirmationTokenExpiry", TOKEN_EXPIRY);
		mockedAuthenticationService = Mockito.spy(authenticationService);
	}

	@Test(expected = UserAlreadyExistAuthenticationException.class)
	public void registerExistingUser() throws UserAlreadyExistAuthenticationException {
		RegisterRequestDto request = new RegisterRequestDto("Adam", "Nagy", "adam@b.com", "test123");
		when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
		authenticationService.register(request);
	}

	@Test
	public void register() throws UserAlreadyExistAuthenticationException {

		RegisterRequestDto request = new RegisterRequestDto("Adam", "Nagy", "adam@b.com", "test123");
		User user = User.builder()
			.firstName("Adam")
			.lastName("Nagy")
			.email("adam@b.com")
			.role(Role.USER)
			.password("shouldBeHashed")
			.createdAt(LocalDateTime.of(2023, 1, 1, 1, 1))
			.build();
		HashMap<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("firstname", user.getFirstName());
		extraClaims.put("lastname", user.getLastName());
		LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 1, 1);
		try (MockedStatic<LocalDateTime> topDateTimeUtilMock = Mockito.mockStatic(LocalDateTime.class)) {
			topDateTimeUtilMock.when(LocalDateTime::now).thenReturn(localDateTime);

			when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
			when(passwordEncoder.encode(Mockito.anyString())).thenReturn("shouldBeHashed");
			when(jwtService.generateToken(extraClaims, user)).thenReturn("jwtToken");
			mockedAuthenticationService.register(request);
			verify(userRepository).existsByEmail(user.getEmail());
			verify(userRepository).save(user);
			verify(mockedAuthenticationService).generateConfirmationEmail(user);
		}
	}

	@Test(expected = UsernameNotFoundException.class)
	public void sendConfirmationEmailToNotExistingEmail() {
		User user = new User();
		when(userRepository.findByEmail("test@b.com")).thenReturn(Optional.of(user));
		authenticationService.sendConfirmationEmail("asd@gmail.com");
	}

	@Test(expected = UserIsAlreadyEnabledException.class)
	public void sendConfirmationEmailToEnabledEmail() {
		User user = User.builder()
			.firstName("Adam")
			.lastName("Nagy")
			.email("adam@b.com")
			.role(Role.USER)
			.password("shouldBeHashed")
			.enabled(true)
			.build();
		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		authenticationService.sendConfirmationEmail("asd@gmail.com");
	}

	@Test
	public void sendConfirmationEmail() {
		UUID token = UUID.randomUUID();
		LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 1, 1);
		try (MockedStatic<LocalDateTime> topDateTimeUtilMock = Mockito.mockStatic(LocalDateTime.class);
				MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
			topDateTimeUtilMock.when(LocalDateTime::now).thenReturn(localDateTime);
			mockedUuid.when(UUID::randomUUID).thenReturn(token);
			User user = User.builder()
				.id(1L)
				.firstName("Adam")
				.lastName("Nagy")
				.email("adam@b.com")
				.role(Role.USER)
				.password("shouldBeHashed")
				.build();
			when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
			mockedAuthenticationService.sendConfirmationEmail(user.getEmail());
			verify(confirmationTokenService).deleteAllByUser(user);
			verify(mockedAuthenticationService).generateConfirmationEmail(user);
		}
	}

	@Test
	public void generateConfirmationEmail() {

		UUID token = UUID.randomUUID();
		LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 1, 1);
		LocalDateTime expiryDateTime = localDateTime.plusMinutes(TOKEN_EXPIRY);
		try (MockedStatic<LocalDateTime> topDateTimeUtilMock = Mockito.mockStatic(LocalDateTime.class);
				MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
			topDateTimeUtilMock.when(LocalDateTime::now).thenReturn(localDateTime);
			mockedUuid.when(UUID::randomUUID).thenReturn(token);
			User user = User.builder()
				.id(1L)
				.firstName("Adam")
				.lastName("Nagy")
				.email("adam@b.com")
				.role(Role.USER)
				.password("shouldBeHashed")
				.build();
			ConfirmationToken confirmationToken = ConfirmationToken.builder()
				.token(String.valueOf(token))
				.createdAt(LocalDateTime.now())
				.expiresAt(expiryDateTime)
				.user(user)
				.build();
			authenticationService.generateConfirmationEmail(user);
			verify(confirmationTokenService).saveConfirmationToken(confirmationToken);
			verify(emailService).send(anyString(), anyString());
		}
	}

	@Test(expected = NoSuchElementException.class)
  public void authenticateUserNotFound() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    authenticationService.login(new AuthenticationRequestDto("asd@gmail.com", "asdasd"));
    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
  }

	@Test
	public void authenticate() {

		User user = User.builder()
			.id(1L)
			.firstName("Adam")
			.lastName("Nagy")
			.email("adam@b.com")
			.role(Role.USER)
			.password("shouldBeHashed")
			.build();
		HashMap<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("firstname", user.getFirstName());
		extraClaims.put("lastname", user.getLastName());
		when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
		when(jwtService.generateToken(extraClaims, user)).thenReturn("testToken");
		AuthenticationResponseDto result = authenticationService
			.login(new AuthenticationRequestDto("user@gmail.com", "asdasd"));
		Assert.assertEquals(result.getToken(), "testToken");
		verify(jwtService).generateToken(extraClaims, user);
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	@Test(expected = UserIsAlreadyEnabledException.class)
	public void confirmRegistrationTokenWithConfirmedToken() {
		final String token = "token";
		ConfirmationToken confirmationToken = ConfirmationToken.builder()
			.token(token)
			.createdAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY))
			.user(User.builder().build())
			.confirmedAt(LocalDateTime.now())
			.build();
		when(confirmationTokenService.getToken(anyString())).thenReturn(Optional.of(confirmationToken));
		authenticationService.confirmRegistrationToken(token);
	}

	@Test(expected = ConfirmationTokenExpiredException.class)
	public void confirmRegistrationTokenWithExpiredToken() {
		final String token = "token";
		ConfirmationToken confirmationToken = ConfirmationToken.builder()
			.token(token)
			.createdAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().minusMinutes(TOKEN_EXPIRY))
			.user(User.builder().build())
			.build();
		when(confirmationTokenService.getToken(anyString())).thenReturn(Optional.of(confirmationToken));
		authenticationService.confirmRegistrationToken(token);

	}

	@Test
	public void confirmRegistrationToken() {

		LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 1, 1);
		LocalDateTime expiryDateTime = localDateTime.plusMinutes(TOKEN_EXPIRY);
		try (MockedStatic<LocalDateTime> topDateTimeUtilMock = Mockito.mockStatic(LocalDateTime.class)) {
			User user = User.builder()
				.id(1L)
				.firstName("Adam")
				.lastName("Nagy")
				.email("adam@b.com")
				.role(Role.USER)
				.createdAt(localDateTime)
				.password("shouldBeHashed")
				.build();
			topDateTimeUtilMock.when(LocalDateTime::now).thenReturn(localDateTime);
			final String token = "token";
			ConfirmationToken confirmationToken = ConfirmationToken.builder()
				.token(token)
				.createdAt(localDateTime)
				.expiresAt(expiryDateTime)
				.user(user)
				.build();
			when(confirmationTokenService.getToken(anyString())).thenReturn(Optional.of(confirmationToken));
			when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
			System.out.println(userRepository.findByEmail(user.getEmail()));
			authenticationService.confirmRegistrationToken(token);
			ArgumentCaptor<ConfirmationToken> tokenCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);
			ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
			verify(confirmationTokenService).saveConfirmationToken(tokenCaptor.capture());
			verify(userRepository).save(userArgumentCaptor.capture());

			Assert.assertEquals(tokenCaptor.getValue().getConfirmedAt(), localDateTime);
			Assert.assertEquals(userArgumentCaptor.getValue().getEnabled(), true);

		}
	}

}