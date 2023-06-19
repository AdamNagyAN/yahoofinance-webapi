package com.adamnagyan.yahoofinancewebapi.services.user_stock;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.mapper.StockHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.user_stock.model.StockHistoryItem;
import com.adamnagyan.yahoofinancewebapi.user_stock.respository.UserStockRepository;
import com.adamnagyan.yahoofinancewebapi.user_stock.service.UserStockHistoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class UserStockHistoryServiceImplTest {

	UserStockHistoryServiceImpl userStockHistoryServiceImpl;

	@Mock
	UserStockRepository userStockRepository;

	@Mock
	StockHistoryMapper stockHistoryMapper;

	User user = User.builder().id(1L).email("test@example.com").build();

	UserStockHistoryItemRequestDto request = UserStockHistoryItemRequestDto.builder()
		.symbol("AAPL")
		.quantity(10.0)
		.transactionFee(1.0)
		.price(100.0)
		.buyDate(DATE)
		.build();

	StockHistoryItem stockHistoryItem = StockHistoryItem.builder()
		.symbol("AAPL")
		.quantity(10.0)
		.transactionFee(1.0)
		.price(100.0)
		.user(user)
		.buyDate(DATE)
		.build();

	private static final LocalDate DATE = LocalDate.of(2021, 1, 1);

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		userStockHistoryServiceImpl = new UserStockHistoryServiceImpl(userStockRepository, stockHistoryMapper);
	}

	@Test
	public void addNewUserStock() {
		userStockHistoryServiceImpl.addNewUserStock(request, user);
		Mockito.verify(userStockRepository).save(stockHistoryItem);
	}

	@Test
	public void updateStockById() {

	}

	@Test
	public void getAllUserStockHistoryItem() {
	}

	@Test
	public void deleteUserStockById() {
	}

	@Test
	public void isItemRelatedToUser() {
	}

}