package com.adamnagyan.yahoofinancewebapi.services.user_stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.StockHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BaseAppExceptionFactory;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.model.user_stock.StockHistoryItem;
import com.adamnagyan.yahoofinancewebapi.repositories.user_stock.UserStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserStockHistoryServiceImpl implements UserStockHistoryService {

	private final UserStockRepository userStockRepository;

	private final StockHistoryMapper stockHistoryMapper;

	@Override
	public void addNewUserStock(UserStockHistoryItemRequestDto request, User user) {
		StockHistoryItem userStockHistory = StockHistoryItem.builder()
			.symbol(request.getSymbol())
			.quantity(request.getQuantity())
			.transactionFee(request.getTransactionFee())
			.price(request.getPrice())
			.user(user)
			.buyDate(request.getBuyDate())
			.build();
		userStockRepository.save(userStockHistory);
	}

	@Override
	@SneakyThrows
	public void updateStockById(Long id, UserStockHistoryItemRequestDto request, User user) {
		if (!isItemRelatedToUser(id, user)) {
			throw BaseAppExceptionFactory.forbidden();
		}
		StockHistoryItem stockHistoryItem = stockHistoryMapper.toStockHistoryItem(request);
		stockHistoryItem.setUser(user);
		stockHistoryItem.setId(id);
		userStockRepository.save(stockHistoryItem);
	}

	public StockHistoryListDto getAllUserStockHistoryItem(User user) {
		return stockHistoryMapper.toStockHistoryList(0, userStockRepository.findAllByUser(user));
	}

	@Override
	@SneakyThrows
	public void deleteUserStockById(Long id, User user) {
		if (!isItemRelatedToUser(id, user)) {
			throw BaseAppExceptionFactory.forbidden();
		}
		userStockRepository.deleteById(id);
	}

	public Boolean isItemRelatedToUser(Long id, User user) {
		StockHistoryItem item = userStockRepository.findById(id).orElseThrow(NoSuchElementException::new);
		return item.getUser().equals(user);
	}

}
