package com.projectY.splitYourBills.service;

import java.util.List;

import com.projectY.splitYourBills.entity.UserExpenseBalanceSheet;
import com.projectY.splitYourBills.model.TransactionsDTO;

public interface BalanceService {
	List<TransactionsDTO> getGroupTransacions(long groupId);
	UserExpenseBalanceSheet minTransfer(List<TransactionsDTO> transactions, long groupId, long userId);
}
