package com.adamnagyan.yahoofinancewebapi.auth.respository;

import com.adamnagyan.yahoofinancewebapi.auth.model.NewPasswordToken;
import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewPasswordTokenRepository extends JpaRepository<NewPasswordToken, Long> {

	void deleteAllByUser(User user);

	Optional<NewPasswordToken> findByToken(String token);

}
