package com.adamnagyan.yahoofinancewebapi.services.user_stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.model.user.User;

public interface UserStockHistoryService {

	void addNewUserStock(UserStockHistoryItemRequestDto request, User user);

	StockHistoryListDto getAllUserStockHistoryItem(User user);

	void updateStockById(Long id, UserStockHistoryItemRequestDto request, User user);

	void deleteUserStockById(Long id, User user);

}
