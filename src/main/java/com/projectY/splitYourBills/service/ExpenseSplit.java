package com.projectY.splitYourBills.service;

import java.util.List;

import com.projectY.splitYourBills.utility.Split;

public interface ExpenseSplit {

    public void validateSplitRequest(List<Split> splitList, double totalAmount);
}
