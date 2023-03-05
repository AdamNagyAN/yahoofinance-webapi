package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.AuthenticationRequestMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;
import com.adamnagyan.yahoofinancewebapi.model.user.Role;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
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
