package com.adamnagyan.yahoofinancewebapi.bootstrap;

import com.adamnagyan.yahoofinancewebapi.model.user.Role;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Profile("dev")
public class Bootstrap implements CommandLineRunner {
  private final UserRepository userRepository;
  @Override
  public void run(String... args) throws Exception {
    initUser();
  }

  private void initUser() {
    User user = User.builder()
            .email("test@gmail.com")
            .password("string")
            .firstName("Adam")
            .lastName("Nagy")
            .role(Role.USER)
            .enabled(true)
            .createdAt(LocalDateTime.now())
            .build();
    userRepository.save(user);
  }
}
