package com.projectY.splitYourBills.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.entity.UserExpenseBalanceSheet;
import com.projectY.splitYourBills.model.Result;
import com.projectY.splitYourBills.service.BalanceService;
import com.projectY.splitYourBills.service.Transaction;

@RestController
@RequestMapping("/api")
public class BalanceController {

	private BalanceService balanceService;
	private Transaction transactionService;

	public BalanceController(BalanceService balanceService, Transaction transactionService) {
		this.balanceService = balanceService;
		this.transactionService = transactionService;
	}

	@GetMapping("balance/{groupId}")
	public ResponseEntity<UserExpenseBalanceSheet> getUserBalances(@PathVariable long groupId, @RequestParam long userId) {
		List<Result> groupTransactions = balanceService.getGroupTransacions(groupId);
		Map<User,Double> sheet = transactionService.minTransfer(groupTransactions, userId);
		UserExpenseBalanceSheet response = balanceService.minTransfer(groupTransactions, groupId, userId);
		return ResponseEntity.ok(response);
	}
}
