package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.exceptions.ConfirmationTokenExpiredException;
import com.adamnagyan.yahoofinancewebapi.model.user.NewPasswordToken;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.repositories.user.NewPasswordTokenRepository;
import com.adamnagyan.yahoofinancewebapi.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewPasswordServiceImpl implements NewPasswordService {

	private final NewPasswordTokenRepository newPasswordTokenRepository;

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	private final EmailService emailService;

	@Value("${auth.new-password-token-expiration-minutes}")
	private Integer expiryTime = 5;

	@Value("${external-apis.reset-password-email}")
	private String redirectLink;

	@Override
	@Transactional
	public void generateNewPasswordToken(String email) {
		User user = userService.getUserByEmail(email);
		newPasswordTokenRepository.deleteAllByUser(user);
		NewPasswordToken newPasswordToken = NewPasswordToken.builder()
			.createdAt(LocalDateTime.now())
			.expiresAt(LocalDateTime.now().plusMinutes(expiryTime))
			.token(UUID.randomUUID().toString())
			.user(user)
			.build();
		newPasswordTokenRepository.save(newPasswordToken);
		String newPasswordLinkWithToken = UriComponentsBuilder.fromHttpUrl(redirectLink)
			.queryParam("token", newPasswordToken.getToken())
			.toUriString();
		emailService.send(email, newPasswordLinkWithToken);
	}

	@SneakyThrows
	@Transactional
	public void changePassword(String token, String newPassword) {
		NewPasswordToken newPasswordToken = newPasswordTokenRepository.findByToken(token)
			.orElseThrow(NoSuchElementException::new);
		if (LocalDateTime.now().isAfter(newPasswordToken.getExpiresAt())) {
			throw new ConfirmationTokenExpiredException();
		}
		User user = newPasswordToken.getUser();
		user.setPassword(passwordEncoder.encode(newPassword));
		userService.updateUser(user);
		newPasswordTokenRepository.deleteAllByUser(user);
	}

}
