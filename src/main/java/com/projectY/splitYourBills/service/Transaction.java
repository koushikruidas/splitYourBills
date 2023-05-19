package com.projectY.splitYourBills.service;

import java.util.List;
import java.util.Map;

import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.model.Result;

public interface Transaction {
	Map<User, Double> minTransfer(List<Result> transactions, long userId);
}
