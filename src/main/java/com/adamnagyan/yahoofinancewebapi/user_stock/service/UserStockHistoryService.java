package com.adamnagyan.yahoofinancewebapi.user_stock.service;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.UserStockHistoryItemRequestDto;

public interface UserStockHistoryService {

	void addNewUserStock(UserStockHistoryItemRequestDto request, User user);

	StockHistoryListDto getAllUserStockHistoryItem(User user);

	void updateStockById(Long id, UserStockHistoryItemRequestDto request, User user);

	void deleteUserStockById(Long id, User user);

}
