package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;
import com.adamnagyan.yahoofinancewebapi.model.user.User;

import java.util.Optional;

public interface ConfirmationTokenService {

	public void saveConfirmationToken(ConfirmationToken token);

	public Optional<ConfirmationToken> getToken(String token);

	public Optional<ConfirmationToken> getToken(User user);

	public void deleteAllByUser(User user);

}
