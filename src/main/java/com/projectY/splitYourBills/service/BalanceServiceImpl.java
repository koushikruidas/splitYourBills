package com.projectY.splitYourBills.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Expense;
import com.projectY.splitYourBills.entity.Split;
import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.entity.UserExpenseBalanceSheet;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.Result;
import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.repo.ExpenseRepository;
import com.projectY.splitYourBills.repo.SplitRepository;
import com.projectY.splitYourBills.repo.UserRepository;

@Service
public class BalanceServiceImpl implements BalanceService {

	private UserRepository userRepository;
    private ExpenseRepository expenseRepository;
    private SplitRepository splitRepository;
    private ModelMapper mapper;

	public BalanceServiceImpl (UserRepository userRepository
			, ExpenseRepository expenseRepository
			, SplitRepository splitRepository
			, ModelMapper mapper) {
		this.userRepository = userRepository;
		this.expenseRepository = expenseRepository;
		this.splitRepository = splitRepository;
		this.mapper = mapper; 
	}
    
    public List<Result> getGroupTransacions(long groupId){
    	List<Expense> expenses = expenseRepository.findByGroupId(groupId);
    	List<Long> expenseIds = expenses.stream().map(i -> i.getId()).collect(Collectors.toList());
    	List<Split> splits = splitRepository.findAllByExpenseId(expenseIds);
    	
    	 List<Result> results = splits.stream()
                 .flatMap(split -> expenses.stream()
                         .filter(expense -> split.getExpense().getId() == expense.getId() && expense.getGroup().getId() == groupId)
                         .map(expense -> Result.builder()
                        		 .fromUser(userRepository.findById(split.getUserId()).get())
                        		 .amount(split.getAmount())
                        		 .toUser(expense.getPaidBy())
                        		 .build()
                        		 ))
                 .collect(Collectors.toList());
    	 
    	 
    	 return results;
    }   
    
    public UserExpenseBalanceSheet minTransfer(List<Result> transactions, long groupId, long userId) {
    	UserExpenseBalanceSheet userExpenseBalanceSheet;
    	User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    	UserDTO userDto = mapper.map(user, UserDTO.class);
    	
    	List<Expense> expenses = expenseRepository.findByGroupId(groupId).stream()
    			.filter(i -> i.getPaidBy().getId() == userId)
    			.collect(Collectors.toList());
    	double totalPayment =  expenses.stream().map(i -> i.getAmount()).reduce(0d,(a, b) -> a + b);
    	
		Map<UserDTO, Double> memberVsBalanceMap = new HashMap<>();
		for(Result txn : transactions) {
			UserDTO fromUser = mapper.map(txn.getFromUser(), UserDTO.class);
			double amount = txn.getAmount();
			UserDTO toUser = mapper.map(txn.getToUser(),UserDTO.class);
			memberVsBalanceMap.put(fromUser, memberVsBalanceMap.getOrDefault(fromUser, 0d) - amount);
			memberVsBalanceMap.put(toUser, memberVsBalanceMap.getOrDefault(toUser, 0d) + amount);
		}
		Map<UserDTO, Double> filteredMap = memberVsBalanceMap.entrySet().stream()
				.filter(i -> i.getKey().getId() != userId)
				.collect(Collectors.toMap(i -> i.getKey(), j -> j.getValue()));
		
		if (totalPayment + memberVsBalanceMap.get(userDto) == 0) {
			userExpenseBalanceSheet = UserExpenseBalanceSheet.builder()
			.userVsBalance(filteredMap)
			.totalPayment(totalPayment)
			.build();
		}else if (totalPayment + memberVsBalanceMap.get(userDto) > 0) 
		{
			userExpenseBalanceSheet = UserExpenseBalanceSheet.builder()
			.userVsBalance(filteredMap)
			.totalPayment(totalPayment)
			.totalYouGetBack(memberVsBalanceMap.get(userDto))
			.build();
		}else {
			userExpenseBalanceSheet = UserExpenseBalanceSheet.builder()
			.userVsBalance(filteredMap)
			.totalPayment(totalPayment)
			.totalYouOwe(memberVsBalanceMap.get(userDto))
			.build();
		}
		
		return userExpenseBalanceSheet;
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
    
}

