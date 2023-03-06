package com.adamnagyan.yahoofinancewebapi.services.user_stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.UserStockHistoryItemDto;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.repositories.user_stock.UserStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStockHistoryServiceImpl implements UserStockHistoryService {
  private final UserStockRepository userStockRepository;


  @Override
  public void addNewUserStock(UserStockHistoryItemDto request, User user) {

  }

  @Override
  public void deleteUserStockById(Long id) {

  }
}
