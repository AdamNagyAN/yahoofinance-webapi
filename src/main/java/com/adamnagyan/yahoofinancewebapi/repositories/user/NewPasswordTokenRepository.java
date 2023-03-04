package com.adamnagyan.yahoofinancewebapi.repositories.user;

import com.adamnagyan.yahoofinancewebapi.model.user.NewPasswordToken;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewPasswordTokenRepository extends JpaRepository<NewPasswordToken, Long> {

	void deleteAllByUser(User user);

	Optional<NewPasswordToken> findByToken(String token);

}
