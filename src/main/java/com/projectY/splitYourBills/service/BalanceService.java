package com.projectY.splitYourBills.service;

import java.util.List;

import com.projectY.splitYourBills.model.TransactionsDTO;
import com.projectY.splitYourBills.model.UserExpenseBalanceSheetDTO;

public interface BalanceService {
	List<TransactionsDTO> getGroupTransacions(long groupId);
	UserExpenseBalanceSheetDTO minTransfer(List<TransactionsDTO> transactions, long groupId, long userId);
}
