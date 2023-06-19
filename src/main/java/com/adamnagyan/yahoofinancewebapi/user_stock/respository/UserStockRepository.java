package com.adamnagyan.yahoofinancewebapi.user_stock.respository;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.user_stock.model.StockHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserStockRepository extends JpaRepository<StockHistoryItem, Long> {

	List<StockHistoryItem> findAllByUser(User user);

	@Override
	Optional<StockHistoryItem> findById(Long id);

	void deleteById(Long id);

}
