package com.adamnagyan.yahoofinancewebapi.repositories.user;

import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

	Optional<ConfirmationToken> findByToken(String token);

	Optional<ConfirmationToken> findByUser(Long userId);

}
