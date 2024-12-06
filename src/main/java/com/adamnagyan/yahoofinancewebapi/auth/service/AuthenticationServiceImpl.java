package com.adamnagyan.yahoofinancewebapi.auth.service;

import com.adamnagyan.yahoofinancewebapi.auth.dto.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.auth.dto.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.auth.dto.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.auth.mapper.AuthenticationRequestMapper;
import com.adamnagyan.yahoofinancewebapi.auth.model.Role;
import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.common.exceptions.UserAlreadyExistAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationRequestMapper authenticationMapper;

	private final AuthenticationManager authenticationManager;

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	private final ConfirmationTokenService confirmationTokenService;

	private final JwtService jwtService;

	@Override
	@Transactional
	public void register(RegisterRequestDto request) throws UserAlreadyExistAuthenticationException {
		if (userService.existsByEmail(request.getEmail())) {
			throw new UserAlreadyExistAuthenticationException();
		}
		User user = User.builder()
			.firstName(request.getFirstname())
			.lastName(request.getLastname())
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(Role.USER)
			.build();
		userService.updateUser(user);
		confirmationTokenService.generateConfirmationEmail(user);
	}

	@Override
	public AuthenticationResponseDto login(AuthenticationRequestDto request) {
		authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = userService.getUserByEmail(request.getEmail());
		HashMap<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("firstname", user.getFirstName());
		extraClaims.put("lastname", user.getLastName());
		String jwtToken = jwtService.generateToken(extraClaims, user);
		return authenticationMapper.authenticationResponseToDTO(jwtToken);
	}

}
