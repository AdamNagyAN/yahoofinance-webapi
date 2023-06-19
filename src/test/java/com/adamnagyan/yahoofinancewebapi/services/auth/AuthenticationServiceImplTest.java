package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.auth.dto.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.auth.dto.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.auth.dto.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.auth.mapper.AuthenticationRequestMapper;
import com.adamnagyan.yahoofinancewebapi.auth.model.Role;
import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.auth.service.AuthenticationServiceImpl;
import com.adamnagyan.yahoofinancewebapi.auth.service.ConfirmationTokenService;
import com.adamnagyan.yahoofinancewebapi.auth.service.JwtService;
import com.adamnagyan.yahoofinancewebapi.auth.service.UserService;
import com.adamnagyan.yahoofinancewebapi.common.exceptions.UserAlreadyExistAuthenticationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {

	AuthenticationServiceImpl authenticationService;

	AuthenticationServiceImpl mockedAuthenticationService;

	AuthenticationRequestMapper authenticationMapper;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	UserService userService;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	ConfirmationTokenService confirmationTokenService;

	@Mock
	JwtService jwtService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		authenticationMapper = AuthenticationRequestMapper.INSTANCE;
		authenticationService = new AuthenticationServiceImpl(authenticationMapper, authenticationManager, userService,
				passwordEncoder, confirmationTokenService, jwtService);
		mockedAuthenticationService = Mockito.spy(authenticationService);
	}

	@Test(expected = UserAlreadyExistAuthenticationException.class)
	public void registerExistingUser() throws UserAlreadyExistAuthenticationException {
		RegisterRequestDto request = new RegisterRequestDto("Adam", "Nagy", "adam@b.com", "test123");
		when(userService.existsByEmail(Mockito.anyString())).thenReturn(true);
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

			when(userService.existsByEmail(Mockito.anyString())).thenReturn(false);
			when(passwordEncoder.encode(Mockito.anyString())).thenReturn("shouldBeHashed");
			when(jwtService.generateToken(extraClaims, user)).thenReturn("jwtToken");
			mockedAuthenticationService.register(request);
			verify(userService).existsByEmail(user.getEmail());
			verify(userService).updateUser(user);
			verify(confirmationTokenService).generateConfirmationEmail(user);
		}
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
		when(userService.getUserByEmail("user@gmail.com")).thenReturn(user);
		when(jwtService.generateToken(extraClaims, user)).thenReturn("testToken");
		AuthenticationResponseDto result = authenticationService
			.login(new AuthenticationRequestDto("user@gmail.com", "asdasd"));
		Assert.assertEquals(result.getToken(), "testToken");
		verify(jwtService).generateToken(extraClaims, user);
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

}