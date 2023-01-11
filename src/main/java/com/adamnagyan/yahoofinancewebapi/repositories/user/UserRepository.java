package com.adamnagyan.yahoofinancewebapi.repositories.user;

import com.adamnagyan.yahoofinancewebapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
