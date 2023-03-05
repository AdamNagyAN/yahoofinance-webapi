package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.model.user.User;

public interface ConfirmationTokenService {

	void generateConfirmationEmail(User user);

	void sendConfirmationEmail(String email);

	void confirmRegistrationToken(String token);

}
