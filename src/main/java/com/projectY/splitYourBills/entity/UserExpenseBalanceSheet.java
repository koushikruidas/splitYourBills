package com.projectY.splitYourBills.entity;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExpenseBalanceSheet {

	private Map<User, Double> userVsBalance;
	private double totalPayment;
	private double totalYouOwe;
	private double totalYouGetBack;

}

