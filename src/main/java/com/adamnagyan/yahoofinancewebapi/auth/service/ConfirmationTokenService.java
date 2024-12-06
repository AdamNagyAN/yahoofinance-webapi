package com.adamnagyan.yahoofinancewebapi.auth.service;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;

public interface ConfirmationTokenService {

	void generateConfirmationEmail(User user);

	void sendConfirmationEmail(String email);

	void confirmRegistrationToken(String token);

}
