package com.adamnagyan.yahoofinancewebapi.repositories.user_stock;

import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.model.user_stock.StockHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserStockRepository extends JpaRepository<StockHistoryItem, Long> {

	List<StockHistoryItem> findAllByUser(User user);

	@Override
	Optional<StockHistoryItem> findById(Long id);

	void deleteById(Long id);

}
