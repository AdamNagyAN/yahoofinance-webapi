package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;
import com.adamnagyan.yahoofinancewebapi.repositories.user.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

	private final ConfirmationTokenRepository confirmationTokenRepository;

	public void saveConfirmationToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}

	@Override
	@SneakyThrows
	public Optional<ConfirmationToken> getToken(String token) {
		return confirmationTokenRepository.findByToken(token);
	}

	@Override
	public Optional<ConfirmationToken> getToken(Long userId) {
		return confirmationTokenRepository.findByUser(userId);
	}

}
