package com.adamnagyan.yahoofinancewebapi.services.user_stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.UserStockHistoryItemDto;
import com.adamnagyan.yahoofinancewebapi.model.user.User;

public interface UserStockHistoryService {
  void addNewUserStock(UserStockHistoryItemDto request, User user);
  void deleteUserStockById(Long id);
}
