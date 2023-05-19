package com.projectY.splitYourBills.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.entity.UserExpenseBalanceSheet;
import com.projectY.splitYourBills.model.Result;
import com.projectY.splitYourBills.repo.UserRepository;

@Service
public class TransactionImpl implements Transaction {
	/*
	 * transactions: {
	 * 	[fromUser, amount, toUser]
	 * 	[2,8250,1],
	 * 	[3,8500,1],
	 * 	[1,5100,3],
	 * 	[2,4950,3]
	 * }
	 */
	@Override
	public Map<User, Double> minTransfer(List<Result> transactions, long userId) {
		Map<User, Double> memberVsBalanceMap = new HashMap<>();
		for(Result txn : transactions) {
			User fromUser = txn.getFromUser();
			double amount = txn.getAmount();
			User toUser = txn.getToUser();
			memberVsBalanceMap.put(fromUser, memberVsBalanceMap.getOrDefault(fromUser, 0d) - amount);
			memberVsBalanceMap.put(toUser, memberVsBalanceMap.getOrDefault(toUser, 0d) + amount);
		}
//		List<Double> balances = memberVsBalanceMap.entrySet().stream()
//			.filter(i -> i.getValue() != 0)
//			.map(i -> i.getValue())
//			.collect(Collectors.toList());
//			;
//		return dfs(balances,0);
		return memberVsBalanceMap;
	}
	
	private int dfs(List<Double> balances, int currentIndex) {
		int minTxnCount = Integer.MAX_VALUE;
		if(balances.size() == 0 || currentIndex >= balances.size()) {
			return 0;
		}
		if(balances.get(currentIndex) == 0) {
			return dfs(balances, currentIndex + 1);
		}
		double currVal = balances.get(currentIndex);
		for(int txnIndex = currentIndex + 1; txnIndex < balances.size(); txnIndex++) {
			double nextVal = balances.get(txnIndex);
			if(currVal * nextVal < 0) {
				balances.set(txnIndex, currVal + nextVal);
				minTxnCount = Math.min(minTxnCount, 1 + dfs(balances,currentIndex+1));
				balances.set(txnIndex, nextVal);
				if(currVal + nextVal == 0) {
					break;
				}
			}
		}
		return minTxnCount;
	}
	
	/*
	 * public static void main(String[] args) { TransactionImpl start = new
	 * TransactionImpl(); List<Double> t1 = new ArrayList<Double>(); List<Double> t2
	 * = new ArrayList<Double>(); List<Double> t3 = new ArrayList<Double>();
	 * List<Double> t4 = new ArrayList<Double>(); List<List<Double>> balances = new
	 * ArrayList<List<Double>>(); t1.addAll(Arrays.asList(2d,8250d,1d));
	 * t2.addAll(Arrays.asList(3d,8500d,1d)); t3.addAll(Arrays.asList(1d,5100d,3d));
	 * t4.addAll(Arrays.asList(2d,4950d,3d));
	 * balances.addAll(Arrays.asList(t1,t2,t3,t4));
	 * System.out.println(start.minTransfer(balances)); }
	 */
}
