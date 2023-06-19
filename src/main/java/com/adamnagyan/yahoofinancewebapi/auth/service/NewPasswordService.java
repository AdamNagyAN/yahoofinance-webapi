package com.adamnagyan.yahoofinancewebapi.auth.service;

public interface NewPasswordService {

	void generateNewPasswordToken(String email);

	void changePassword(String token, String newPassword);

}
