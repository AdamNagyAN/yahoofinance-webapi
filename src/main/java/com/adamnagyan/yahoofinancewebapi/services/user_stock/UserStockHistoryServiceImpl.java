package com.adamnagyan.yahoofinancewebapi.services.user_stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.StockHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.model.user_stock.StockHistoryItem;
import com.adamnagyan.yahoofinancewebapi.repositories.user_stock.UserStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            .build();
    userStockRepository.save(userStockHistory);
  }

  @Override
  public void updateStockById(Long id, UserStockHistoryItemRequestDto request) {

  }

  public StockHistoryListDto getAllUserStockHistoryItem(User user) {
    return stockHistoryMapper.toStockHistoryList(0, userStockRepository.findAllByUser(user));
  }

  @Override
  public void deleteUserStockById(Long id) {
    //TODO: if stock related to user
    userStockRepository.deleteById(id);
  }
}
