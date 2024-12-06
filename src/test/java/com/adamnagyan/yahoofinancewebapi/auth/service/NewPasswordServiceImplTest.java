package com.adamnagyan.yahoofinancewebapi.auth.service;

import com.adamnagyan.yahoofinancewebapi.auth.model.NewPasswordToken;
import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.auth.respository.NewPasswordTokenRepository;
import com.adamnagyan.yahoofinancewebapi.common.exceptions.ConfirmationTokenExpiredException;
import com.adamnagyan.yahoofinancewebapi.common.services.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class NewPasswordServiceImplTest {

	private static final String TEST_EMAIL = "test@gmail.com";

	private static final String REDIRECT_LINK = "https://test";

	private static final Integer TOKEN_EXPIRY = 15;

	private NewPasswordServiceImpl newPasswordService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private NewPasswordTokenRepository newPasswordTokenRepository;

	@Mock
	private UserService userService;

	@Mock
	private EmailService emailService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.newPasswordService = new NewPasswordServiceImpl(newPasswordTokenRepository, userService, passwordEncoder,
				emailService);
		ReflectionTestUtils.setField(newPasswordService, "expiryTime", TOKEN_EXPIRY);
		ReflectionTestUtils.setField(newPasswordService, "expiryTime", TOKEN_EXPIRY);
		ReflectionTestUtils.setField(newPasswordService, "redirectLink", REDIRECT_LINK);

	}

	@Test
	public void generateNewPasswordToken() {
		UUID token = UUID.randomUUID();
		LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 1, 1);
		try (MockedStatic<LocalDateTime> topDateTimeUtilMock = Mockito.mockStatic(LocalDateTime.class);
				MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
			topDateTimeUtilMock.when(LocalDateTime::now).thenReturn(localDateTime);
			mockedUuid.when(UUID::randomUUID).thenReturn(token);

			User user = User.builder().email(TEST_EMAIL).build();
			NewPasswordToken newPasswordToken = NewPasswordToken.builder()
				.createdAt(LocalDateTime.now())
				.expiresAt(LocalDateTime.now().plusMinutes(15))
				.token(UUID.randomUUID().toString())
				.user(user)
				.build();
			when(userService.getUserByEmail(anyString())).thenReturn(user);

			newPasswordService.generateNewPasswordToken(TEST_EMAIL);

			verify(userService).getUserByEmail(TEST_EMAIL);
			verify(newPasswordTokenRepository).deleteAllByUser(user);
			verify(newPasswordTokenRepository).save(newPasswordToken);
			verify(emailService).send(eq(TEST_EMAIL), anyString());

		}
	}

	@Test
	public void changePassword() {
		UUID token = UUID.randomUUID();
		LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 1, 1);
		try (MockedStatic<LocalDateTime> topDateTimeUtilMock = Mockito.mockStatic(LocalDateTime.class);
				MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
			topDateTimeUtilMock.when(LocalDateTime::now).thenReturn(localDateTime);
			mockedUuid.when(UUID::randomUUID).thenReturn(token);
			User user = User.builder().email(TEST_EMAIL).password("password").build();

			NewPasswordToken newPasswordToken = NewPasswordToken.builder()
				.createdAt(LocalDateTime.now())
				.expiresAt(LocalDateTime.now().plusMinutes(15))
				.token(UUID.randomUUID().toString())
				.user(user)
				.build();

			when(newPasswordTokenRepository.findByToken(token.toString())).thenReturn(Optional.of(newPasswordToken));
			when(passwordEncoder.encode("newPassword")).thenReturn("hashedNewPassword");

			newPasswordService.changePassword(token.toString(), "newPassword");

			verify(newPasswordTokenRepository).findByToken(token.toString());
			verify(passwordEncoder).encode("newPassword");
			verify(userService).updateUser(User.builder().email(TEST_EMAIL).password("hashedNewPassword").build());
			verify(newPasswordTokenRepository).deleteAllByUser(user);
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void changePasswordTokenNotFound() {
		UUID token = UUID.randomUUID();
		when(newPasswordTokenRepository.findByToken(token.toString())).thenReturn(Optional.empty());

		newPasswordService.changePassword(token.toString(), "newPassword");

		verify(newPasswordTokenRepository).findByToken(token.toString());
	}

	@Test(expected = ConfirmationTokenExpiredException.class)
	public void changePasswordTokenExpired() {
		UUID token = UUID.randomUUID();
		User user = User.builder().email(TEST_EMAIL).password("password").build();
		NewPasswordToken newPasswordToken = NewPasswordToken.builder()
			.createdAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().minusMinutes(15))
			.token(UUID.randomUUID().toString())
			.user(user)
			.build();

		when(newPasswordTokenRepository.findByToken(token.toString())).thenReturn(Optional.of(newPasswordToken));

		newPasswordService.changePassword(token.toString(), "newPassword");

		verify(newPasswordTokenRepository).findByToken(token.toString());
	}

}
