package com.projectY.splitYourBills.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projectY.splitYourBills.entity.UserExpenseBalanceSheet;
import com.projectY.splitYourBills.model.TransactionsDTO;
import com.projectY.splitYourBills.service.BalanceService;

@RestController
@RequestMapping("/api")
public class BalanceController {

	private BalanceService balanceService;

	public BalanceController(BalanceService balanceService) {
		this.balanceService = balanceService;
	}

	@GetMapping("balance/{groupId}")
	public ResponseEntity<UserExpenseBalanceSheet> getUserBalances(@PathVariable long groupId, @RequestParam long userId) {
		List<TransactionsDTO> groupTransactions = balanceService.getGroupTransacions(groupId);
		UserExpenseBalanceSheet response = balanceService.minTransfer(groupTransactions, groupId, userId);
		return ResponseEntity.ok(response);
	}
}
