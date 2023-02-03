package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.AuthenticationRequestMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.ConfirmationTokenExpiredException;
import com.adamnagyan.yahoofinancewebapi.exceptions.ConfirmationTokenNotFoundException;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;
import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;
import com.adamnagyan.yahoofinancewebapi.model.user.Role;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationRequestMapper authenticationMapper;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ConfirmationTokenService confirmationTokenService;
  private final JwtService jwtService;

  //TODO: from application.yml
  private Integer confirmationTokenExpiry = 10;

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
    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken = ConfirmationToken.builder()
            .token(token)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(confirmationTokenExpiry))
            .user(user)
            .build();
    confirmationTokenService.saveConfirmationToken(confirmationToken);
    //TODO : send email
    return authenticationMapper.authenticationResponseToDTO(jwtToken);
  }

  @Override
  public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    return authenticationMapper.authenticationResponseToDTO(jwtToken);
  }

  @Override
  @SneakyThrows
  public void confirmRegistrationToken(String token) {
    ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(ConfirmationTokenNotFoundException::new);

    if (confirmationToken.getConfirmedAt() != null) {
      throw new Exception("Token is already confirmed");
    }

    if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new ConfirmationTokenExpiredException();
    }

    confirmationToken.setConfirmedAt(LocalDateTime.now());
    confirmationTokenService.saveConfirmationToken(confirmationToken);
    User user = userRepository.findByEmail(confirmationToken.getUser().getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    user.setEnabled(true);
    userRepository.save(user);
  }
}
