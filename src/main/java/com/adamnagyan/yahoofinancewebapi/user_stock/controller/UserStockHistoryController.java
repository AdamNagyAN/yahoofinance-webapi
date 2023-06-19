package com.adamnagyan.yahoofinancewebapi.user_stock.controller;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.auth.service.UserService;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.service.UserStockHistoryService;
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
