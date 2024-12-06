package com.adamnagyan.yahoofinancewebapi.user_stock.service;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.common.exceptions.BaseAppException;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.mapper.StockHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.user_stock.model.StockHistoryItem;
import com.adamnagyan.yahoofinancewebapi.user_stock.respository.UserStockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

public class UserStockHistoryServiceImplTest {

	UserStockHistoryServiceImpl userStockHistoryServiceImpl;

	@Mock
	UserStockRepository userStockRepository;

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
		stockHistoryMapper = StockHistoryMapper.INSTANCE;
		userStockHistoryServiceImpl = new UserStockHistoryServiceImpl(userStockRepository, stockHistoryMapper);
	}

	@Test
	public void updateStockById() {
		StockHistoryItem stockHistoryItem = StockHistoryItem.builder()
			.symbol("AAPL")
			.quantity(10.0)
			.transactionFee(1.0)
			.price(100.0)
			.user(user)
			.buyDate(DATE)
			.build();
		Mockito.when(userStockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stockHistoryItem));
		userStockHistoryServiceImpl.updateStockById(1L, request, user);
		stockHistoryItem.setUser(user);
		stockHistoryItem.setId(1L);
		Mockito.verify(userStockRepository).save(stockHistoryItem);
	}

	@Test(expected = BaseAppException.class)
	public void updateStockByIdWithWrongUser() {
		StockHistoryItem stockHistoryItem = StockHistoryItem.builder()
			.symbol("AAPL")
			.quantity(10.0)
			.transactionFee(1.0)
			.price(100.0)
			.user(user)
			.buyDate(DATE)
			.build();
		Mockito.when(userStockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stockHistoryItem));
		userStockHistoryServiceImpl.updateStockById(1L, request,
				User.builder().email("test2@example.com").id(2L).build());
		stockHistoryItem.setUser(user);
		stockHistoryItem.setId(1L);
		Mockito.verify(userStockRepository).save(stockHistoryItem);
	}

	@Test
	public void getAllUserStockHistoryItem() {
		Mockito.when(userStockRepository.findAllByUser(Mockito.any(User.class)))
			.thenReturn(java.util.List.of(stockHistoryItem));
		StockHistoryListDto stockHistoryListDto = userStockHistoryServiceImpl.getAllUserStockHistoryItem(user);
		Mockito.verify(userStockRepository).findAllByUser(Mockito.any(User.class));
		Assertions.assertEquals(stockHistoryListDto.getStockHistoryItems().size(), 1);
	}

	@Test
	public void deleteUserStockById() {
		Mockito.when(userStockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stockHistoryItem));
		userStockHistoryServiceImpl.deleteUserStockById(1L, user);
		Mockito.verify(userStockRepository).deleteById(Mockito.anyLong());
	}

	@Test(expected = BaseAppException.class)
	public void deleteUserStockByIdWithWrongUser() {
		Mockito.when(userStockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stockHistoryItem));
		userStockHistoryServiceImpl.deleteUserStockById(1L, User.builder().email("test2@example.com").id(2L).build());
	}

}