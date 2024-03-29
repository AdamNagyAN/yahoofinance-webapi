package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.model.user.User;
import com.adamnagyan.yahoofinancewebapi.services.auth.UserService;
import com.adamnagyan.yahoofinancewebapi.services.user_stock.UserStockHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/stock-history")
@Slf4j
public class UserStockHistoryController {

	private final UserStockHistoryService userStockHistoryService;

	private final UserService userService;

	private User getUserFromContext() {
		User userFromContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userFromContext;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public StockHistoryListDto getAllStockByUser() {
		return userStockHistoryService.getAllUserStockHistoryItem(getUserFromContext());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addNewStock(@Valid @RequestBody UserStockHistoryItemRequestDto request) {
		userStockHistoryService.addNewUserStock(request, getUserFromContext());
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	public void updateStock(@PathVariable Long id, @Valid @RequestBody UserStockHistoryItemRequestDto request) {
		userStockHistoryService.updateStockById(id, request, getUserFromContext());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteStock(@PathVariable Long id) {
		userStockHistoryService.deleteUserStockById(id, getUserFromContext());
	}

}
