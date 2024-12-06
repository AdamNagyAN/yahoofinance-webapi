package com.adamnagyan.yahoofinancewebapi.auth.service;

import com.adamnagyan.yahoofinancewebapi.auth.model.ConfirmationToken;
import com.adamnagyan.yahoofinancewebapi.auth.model.Role;
import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.auth.respository.ConfirmationTokenRepository;
import com.adamnagyan.yahoofinancewebapi.common.exceptions.ConfirmationTokenExpiredException;
import com.adamnagyan.yahoofinancewebapi.common.exceptions.UserIsAlreadyEnabledException;
import com.adamnagyan.yahoofinancewebapi.common.services.EmailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ConfirmationTokenServiceImplTest {

	ConfirmationTokenService confirmationTokenService;

	ConfirmationTokenService spiedConfirmationTokenService;

	@Mock
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Mock
	private UserService userService;

	@Mock
	private EmailService emailService;

	private static final String CONFIRM_URL = "https://test";

	private static final Integer TOKEN_EXPIRY = 15;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		confirmationTokenService = new ConfirmationTokenServiceImpl(confirmationTokenRepository, userService,
				emailService);
		ReflectionTestUtils.setField(confirmationTokenService, "confirmUrl", CONFIRM_URL);
		ReflectionTestUtils.setField(confirmationTokenService, "confirmationTokenExpiry", TOKEN_EXPIRY);
		spiedConfirmationTokenService = spy(confirmationTokenService);
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
			confirmationTokenService.generateConfirmationEmail(user);
			verify(confirmationTokenRepository).save(confirmationToken);
			verify(emailService).send(anyString(), anyString());
		}
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
			when(userService.getUserByEmail(Mockito.anyString())).thenReturn(user);
			spiedConfirmationTokenService.sendConfirmationEmail(user.getEmail());
			verify(confirmationTokenRepository).deleteAllByUser(user);
			verify(spiedConfirmationTokenService).generateConfirmationEmail(user);
		}
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
		when(userService.getUserByEmail(Mockito.anyString())).thenReturn(user);
		confirmationTokenService.sendConfirmationEmail("asd@gmail.com");
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
			when(confirmationTokenRepository.findByToken(anyString())).thenReturn(Optional.of(confirmationToken));
			when(userService.getUserByEmail(anyString())).thenReturn(user);
			System.out.println(userService.getUserByEmail(user.getEmail()));
			confirmationTokenService.confirmRegistrationToken(token);
			ArgumentCaptor<ConfirmationToken> tokenCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);
			ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
			verify(confirmationTokenRepository).save(tokenCaptor.capture());
			verify(userService).updateUser(userArgumentCaptor.capture());

			Assert.assertEquals(userArgumentCaptor.getValue().getEnabled(), true);

		}
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
		when(confirmationTokenRepository.findByToken(anyString())).thenReturn(Optional.of(confirmationToken));
		confirmationTokenService.confirmRegistrationToken(token);
	}

}