package com.projectY.splitYourBills.entity;

import java.util.Map;

import com.projectY.splitYourBills.model.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExpenseBalanceSheet {

	private Map<UserDTO, Double> userVsBalance;
	private double totalPayment;
	private double totalYouOwe;
	private double totalYouGetBack;

}

