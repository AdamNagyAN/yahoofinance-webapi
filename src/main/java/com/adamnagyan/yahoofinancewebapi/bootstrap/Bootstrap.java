package com.adamnagyan.yahoofinancewebapi.bootstrap;

import com.adamnagyan.yahoofinancewebapi.auth.model.Role;
import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.auth.respository.UserRepository;
import com.adamnagyan.yahoofinancewebapi.user_stock.model.StockHistoryItem;
import com.adamnagyan.yahoofinancewebapi.user_stock.respository.UserStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Profile("dev")
public class Bootstrap implements CommandLineRunner {

	private final UserRepository userRepository;

	private final UserStockRepository userStockRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		initUser();
		initStockHistory();
	}

	private void initUser() {
		User user = User.builder()
			.email("test@gmail.com")
			.password(passwordEncoder.encode("string"))
			.firstName("Adam")
			.lastName("Nagy")
			.role(Role.USER)
			.enabled(true)
			.createdAt(LocalDateTime.now())
			.build();
		userRepository.save(user);
		User user2 = User.builder()
			.email("test2@gmail.com")
			.password(passwordEncoder.encode("string"))
			.firstName("Adam2")
			.lastName("Nagy2")
			.role(Role.USER)
			.enabled(true)
			.createdAt(LocalDateTime.now())
			.build();
		userRepository.save(user2);
	}

	private void initStockHistory() {
		User user = userRepository.findByEmail("test@gmail.com").orElseThrow();
		User user2 = userRepository.findByEmail("test2@gmail.com").orElseThrow();

		userStockRepository.save(StockHistoryItem.builder()
			.buyDate(LocalDate.of(2023, 1, 1))
			.symbol("MMM")
			.price(133.0)
			.quantity(1.3)
			.transactionFee(0.93)
			.user(user)
			.build());
		userStockRepository.save(StockHistoryItem.builder()
			.buyDate(LocalDate.of(2023, 1, 2))
			.symbol("CVX")
			.price(129.0)
			.quantity(4.0)
			.transactionFee(0.9)
			.user(user)
			.build());

		userStockRepository.save(StockHistoryItem.builder()
			.buyDate(LocalDate.of(2023, 1, 13))
			.symbol("APPL")
			.price(140.0)
			.quantity(1.5)
			.transactionFee(1.0)
			.user(user2)
			.build());
	}

}
