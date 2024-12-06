package com.adamnagyan.yahoofinancewebapi.auth.service;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.auth.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

}
