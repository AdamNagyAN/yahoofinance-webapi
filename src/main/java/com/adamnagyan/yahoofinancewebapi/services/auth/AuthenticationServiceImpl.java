package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.auth.AuthenticationMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;
import com.adamnagyan.yahoofinancewebapi.model.user.Role;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationMapper authenticationMapper;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public AuthenticationResponseDto register(RegisterRequestDto request) throws UserAlreadyExistAuthenticationException {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new UserAlreadyExistAuthenticationException();
    }
    User user = User.builder()
            .firstName(request.getFirstname())
            .lastName(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
    userRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
    return authenticationMapper.authenticationResponseToDTO(jwtToken);
  }

  @Override
  public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    return authenticationMapper.authenticationResponseToDTO(jwtToken);
  }
}
