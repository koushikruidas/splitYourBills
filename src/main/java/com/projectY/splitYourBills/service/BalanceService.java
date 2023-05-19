package com.projectY.splitYourBills.service;

import java.util.List;

import com.projectY.splitYourBills.entity.UserExpenseBalanceSheet;
import com.projectY.splitYourBills.model.Result;

public interface BalanceService {
	List<Result> getGroupTransacions(long groupId);
	UserExpenseBalanceSheet minTransfer(List<Result> transactions, long groupId, long userId);
}
