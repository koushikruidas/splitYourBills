package com.projectY.splitYourBills.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Expense;
import com.projectY.splitYourBills.entity.Split;
import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.Path;
import com.projectY.splitYourBills.model.SNode;
import com.projectY.splitYourBills.model.SPair;
import com.projectY.splitYourBills.model.TransactionsDTO;
import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.model.UserExpenseBalanceSheetDTO;
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
    
    public List<TransactionsDTO> getGroupTransacions(long groupId){
    	List<Expense> expenses = expenseRepository.findByGroupId(groupId);
    	List<Long> expenseIds = expenses.stream().map(i -> i.getId()).collect(Collectors.toList());
    	List<Split> splits = splitRepository.findAllByExpenseId(expenseIds);
    	
    	 List<TransactionsDTO> results = splits.stream()
                 .flatMap(split -> expenses.stream()
                         .filter(expense -> split.getExpense().getId() == expense.getId())
                         .map(expense -> TransactionsDTO.builder()
                        		 .fromUser(userRepository.findById(split.getUserId()).get())
                        		 .amount(split.getAmount())
                        		 .toUser(expense.getPaidBy())
                        		 .build()
                        		 ))
                 .collect(Collectors.toList());
    	 
    	 
    	 return results;
    }   
    
	/*
	 * transactions: {
	 * 	[fromUser, amount, toUser]
	 * 	[2,8250,1],
	 * 	[3,8500,1],
	 * 	[1,5100,3],
	 * 	[2,4950,3]
	 * }
	 */    
    public UserExpenseBalanceSheetDTO minTransfer(List<TransactionsDTO> transactions, long groupId, long userId) {
    	UserExpenseBalanceSheetDTO userExpenseBalanceSheet;
    	User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    	UserDTO userDto = mapper.map(user, UserDTO.class);
    	
    	List<Expense> expenses = expenseRepository.findByGroupId(groupId).stream()
    			.filter(i -> i.getPaidBy().getId() == userId)
    			.collect(Collectors.toList());
    	double totalPayment =  expenses.stream().map(i -> i.getAmount()).reduce(0d,(a, b) -> a + b);
    	
		Map<UserDTO, Double> memberVsBalanceMap = new HashMap<>();
		for(TransactionsDTO txn : transactions) {
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
			userExpenseBalanceSheet = UserExpenseBalanceSheetDTO.builder()
			.userVsBalance(filteredMap)
			.totalPayment(totalPayment)
			.build();
		}else if (totalPayment + memberVsBalanceMap.get(userDto) > 0) 
		{
			userExpenseBalanceSheet = UserExpenseBalanceSheetDTO.builder()
			.userVsBalance(filteredMap)
			.totalPayment(totalPayment)
			.totalYouGetBack(memberVsBalanceMap.get(userDto))
			.build();
		}else {
			userExpenseBalanceSheet = UserExpenseBalanceSheetDTO.builder()
			.userVsBalance(filteredMap)
			.totalPayment(totalPayment)
			.totalYouOwe(memberVsBalanceMap.get(userDto))
			.build();
		}
		
		List<SNode> positive = memberVsBalanceMap.entrySet().stream()
				.filter(i -> i.getValue() > 0)
				.map(i -> SNode.builder()
						.user(i.getKey())
						.amt(i.getValue())
						.build()
						)
				.collect(Collectors.toList());
		
		List<SNode> negative = memberVsBalanceMap.entrySet().stream()
				.filter(i -> i.getValue() < 0)
				.map(i -> SNode.builder()
						.user(i.getKey())
						.amt(i.getValue())
						.build()
						)
				.collect(Collectors.toList());
		SPair settlements = recur(positive, negative);
		System.out.println(settlements.toString());
		return userExpenseBalanceSheet;
	}
	
	private SPair recur(List<SNode> pos, List<SNode> neg) {
		if (pos.size() == 0 && neg.size() == 0) {
			return new SPair(0, new ArrayList<Path>());
		}

		double negVal = neg.get(0).getAmt();

		// find a perfect +ve value
		int min = Integer.MAX_VALUE;
		SPair bestMatch = new SPair(0, new ArrayList<Path>());
		double amt = -1;
		double amtToBePaid = -1;
		UserDTO userWhoOwes = null;
		for (int i = 0; i < pos.size(); i++) {

			// copy of current lists
			ArrayList<SNode> new_pos = new ArrayList<>(pos);
			ArrayList<SNode> new_neg = new ArrayList<>(neg);

			// Assuming both the items will become 0. This the best case.
			new_pos.remove(i);
			new_neg.remove(0);

			if (pos.get(i).getAmt() == -1 * negVal) {
				// Best case scenario
				// Nothing to do as already both are removed from the respective lists
				amt = negVal;
			} else if (pos.get(i).getAmt() > -1 * negVal) {
				// Negative will become 0 but there will be some +ve left
				new_pos.add(new SNode(pos.get(i).getAmt() + negVal, pos.get(i).getUser()));
				amt = negVal;
			} else {
				// There will be some -ve left
				new_neg.add(new SNode(pos.get(i).getAmt() + negVal, neg.get(0).getUser()));
				amt = pos.get(i).getAmt();
			}
			// Take the best match among all the +ve
			SPair temp = recur(new_pos, new_neg);
			if (temp.getTransactionCount() < min) {
				min = temp.getTransactionCount();
				bestMatch = temp;
				amtToBePaid = Math.abs(amt);
				userWhoOwes = pos.get(i).getUser();
			}
		}
		
		Path path = new Path(neg.get(0).getUser(), userWhoOwes, amtToBePaid);
		
		bestMatch.setTransactionCount(1 + min);
		bestMatch.getPath().add(path);

		return bestMatch;
	}
    
}

