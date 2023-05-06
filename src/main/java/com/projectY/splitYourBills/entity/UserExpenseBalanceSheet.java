package com.projectY.splitYourBills.entity;

import java.util.HashMap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserExpenseBalanceSheet {

	private HashMap<String, Balance> userVsBalance;
	private double totalYourExpense;

	private double totalPayment;

	private double totalYouOwe;
	private double totalYouGetBack;

//    public UserExpenseBalanceSheet(){
//
//        userVsBalance = new HashMap<>();
//        totalYourExpense = 0;
//        totalYouOwe = 0;
//        totalYouGetBack = 0;
//    }
//
//    public Map<String, Balance> getUserVsBalance() {
//        return userVsBalance;
//    }
//
//    public double getTotalYourExpense() {
//        return totalYourExpense;
//    }
//
//    public void setTotalYourExpense(double totalYourExpense) {
//        this.totalYourExpense = totalYourExpense;
//    }
//
//    public double getTotalYouOwe() {
//        return totalYouOwe;
//    }
//
//    public void setTotalYouOwe(double totalYouOwe) {
//        this.totalYouOwe = totalYouOwe;
//    }
//
//    public double getTotalYouGetBack() {
//        return totalYouGetBack;
//    }
//
//    public void setTotalYouGetBack(double totalYouGetBack) {
//        this.totalYouGetBack = totalYouGetBack;
//    }
//
//    public double getTotalPayment() {
//        return totalPayment;
//    }
//
//    public void setTotalPayment(double totalPayment) {
//        this.totalPayment = totalPayment;
//    }
}

