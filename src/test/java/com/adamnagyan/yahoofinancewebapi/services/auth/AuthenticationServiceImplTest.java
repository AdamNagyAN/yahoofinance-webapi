package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.AuthenticationRequestMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserIsAlreadyEnabledException;
import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;
import com.adamnagyan.yahoofinancewebapi.model.user.Role;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.repositories.user.UserRepository;
import com.adamnagyan.yahoofinancewebapi.services.email.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class AuthenticationServiceImplTest {
  AuthenticationService authenticationService;
  @Mock
  AuthenticationRequestMapper authenticationMapper;
  @Mock
  AuthenticationManager authenticationManager;
  @Mock
  UserRepository userRepository;
  @Mock
  PasswordEncoder passwordEncoder;
  @Mock
  ConfirmationTokenService confirmationTokenService;
  @Mock
  EmailService emailService;
  @Mock
  JwtService jwtService;
  private Integer confirmationTokenExpiry = 15;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    authenticationService = new AuthenticationServiceImpl(authenticationMapper, authenticationManager, userRepository, passwordEncoder, confirmationTokenService, emailService, jwtService);
  }

  @Test(expected = UserAlreadyExistAuthenticationException.class)
  public void registerExistingUser() throws UserAlreadyExistAuthenticationException {
    RegisterRequestDto request = new RegisterRequestDto(
            "Adam",
            "Nagy",
            "adam@b.com",
            "test123");
    User user = new User();
    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    authenticationService.register(request);
  }

  @Test
  public void register() throws UserAlreadyExistAuthenticationException {
    RegisterRequestDto request = new RegisterRequestDto(
            "Adam",
            "Nagy",
            "adam@b.com",
            "test123");
    User user = new User(null,
            "Adam",
            "Nagy",
            "adam@b.com",
            "hashedPw",
            Role.USER,
            false);
    HashMap<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("firstname", user.getFirstName());
    extraClaims.put("lastname", user.getLastName());

    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
    Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("hashedPw");
    Mockito.when(jwtService.generateToken(extraClaims, user)).thenReturn("jwtToken");
    authenticationService.register(request);
    Mockito.verify(userRepository).save(user);
    Mockito.verify(jwtService).generateToken(extraClaims, user);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void sendConfirmationEmailToNotExistingEmail() {
    User user = new User();
    Mockito.when(userRepository.findByEmail("test@b.com")).thenReturn(Optional.of(user));
    authenticationService.sendConfirmationEmail("asd@gmail.com");
  }

  @Test(expected = UserIsAlreadyEnabledException.class)
  public void sendConfirmationEmailToEnabledEmail() {
    User user = new User(null,
            "Adam",
            "Nagy",
            "adam@b.com",
            "hashedPw",
            Role.USER,
            true);
    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    authenticationService.sendConfirmationEmail("asd@gmail.com");
  }

  @Test
  public void sendConfirmationEmail() {
    UUID token = UUID.randomUUID();
    User user = new User(null,
            "Adam",
            "Nagy",
            "adam@b.com",
            "hashedPw",
            Role.USER,
            false);
    ConfirmationToken confirmationToken = ConfirmationToken.builder()
            .token(token.toString())
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(confirmationTokenExpiry))
            .user(user)
            .build();
    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    Mockito.when(UUID::randomUUID).thenReturn(token);

    authenticationService.sendConfirmationEmail("asd@gmail.com");
    Mockito.verify(confirmationTokenService).saveConfirmationToken(confirmationToken);
  }

  void authenticate() {
  }

  void confirmRegistrationToken() {

  }
}