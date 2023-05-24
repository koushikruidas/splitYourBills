package com.projectY.splitYourBills.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExpenseBalanceSheetDTO {

	private Map<UserDTO, Double> userVsBalance;
	private double totalPayment;
	private double totalYouOwe;
	private double totalYouGetBack;

}

