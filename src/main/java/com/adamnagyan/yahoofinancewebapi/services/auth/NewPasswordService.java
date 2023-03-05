package com.adamnagyan.yahoofinancewebapi.services.auth;

public interface NewPasswordService {

	void generateNewPasswordToken(String email);

	void changePassword(String token, String newPassword);

}
