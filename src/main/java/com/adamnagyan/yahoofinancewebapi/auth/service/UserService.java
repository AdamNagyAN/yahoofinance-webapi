package com.adamnagyan.yahoofinancewebapi.auth.service;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;

public interface UserService {

	User getUserByEmail(String email);

	void updateUser(User user);

	Boolean existsByEmail(String email);

}
