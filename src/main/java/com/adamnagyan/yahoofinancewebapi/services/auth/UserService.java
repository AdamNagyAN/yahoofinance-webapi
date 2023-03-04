package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.model.user.User;

public interface UserService {

	User getUserByEmail(String email);

	void updateUser(User user);

	Boolean existsByEmail(String email);

}
