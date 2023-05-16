
package com.projectY.splitYourBills.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Expense;
import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.ExpenseDTO;
import com.projectY.splitYourBills.repo.ExpenseRepository;
import com.projectY.splitYourBills.repo.GroupRepository;
import com.projectY.splitYourBills.repo.UserRepository;
import com.projectY.splitYourBills.utility.ExpenseSplitType;
import com.projectY.splitYourBills.utility.Split;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	private ExpenseRepository expenseRepository;
	private GroupRepository groupRepository;
	private UserRepository userRepository;
	private ModelMapper mapper;
	
	
	public ExpenseServiceImpl(ExpenseRepository expenseRepository, ModelMapper mapper
			, GroupRepository groupRepository
			, UserRepository userRepository) {
		this.expenseRepository = expenseRepository;
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	@Override 
	public ExpenseDTO createExpense(ExpenseDTO expenseDto) {
		List<User> memberList = userRepository.findAllByIds(expenseDto.getMemberIds());
		
		List<Split> splits = ExpenseServiceImpl.split(expenseDto.getSplitType(), expenseDto.getAmount()
				, memberList, expenseDto.getMemberShare());
		
		Expense expense = Expense.builder()
							.amount(expenseDto.getAmount())
							.date(expenseDto.getDate())
							.description(expenseDto.getDescription())
							.group(groupRepository.findById(expenseDto.getGroupId()).get())
							.paidBy(userRepository.findById(
									expenseDto.getPaidById())
									.orElseThrow(() -> new ResourceNotFoundException("user not found"))
									)
							.users(memberList)
							.splitType(expenseDto.getSplitType())
							.splitDetails(splits)
							.build();
		for(Split sp : splits) {
			sp.setExpense(expense);
		}
		Expense savedExpense = expenseRepository.save(expense); 
		return mapper.map(savedExpense, ExpenseDTO.class); 
	}

	@Override
	public ExpenseDTO findById(Long expenseId) {
		Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
		if (expenseOptional.isPresent()) {
			Expense expense = expenseOptional.get();
			return mapper.map(expense, ExpenseDTO.class);
		}
		throw new ResourceNotFoundException("Expense with ID " + expenseId + " not found");
	}

	@Override
	public List<ExpenseDTO> findAll() {
		List<Expense> expenses = expenseRepository.findAll();
		return expenses.stream().map(expense -> mapper.map(expense, ExpenseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteById(Long expenseId) {
		Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
		if (expenseOptional.isPresent()) {
			expenseRepository.deleteById(expenseId);
		} else {
			throw new ResourceNotFoundException("Expense with ID " + expenseId + " not found");
		}
	}

	@Override
	public ExpenseDTO update(Long expenseId, ExpenseDTO ExpenseDTO) {
		Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
		if (expenseOptional.isPresent()) {
			Expense expense = mapper.map(ExpenseDTO, Expense.class);
			Expense updatedExpense = expenseRepository.save(expense);
			return mapper.map(updatedExpense, ExpenseDTO.class);
		} else {
			throw new ResourceNotFoundException("Expense with ID " + expenseId + " not found");
		}
	}
	
	public static List<Split> split(ExpenseSplitType type, double totalAmount, List<User> members,
			Map<Long, Double> memberShare) {
		List<Split> splits = new ArrayList<>();
		if (type == ExpenseSplitType.EQUAL) {
			double splitAmount = totalAmount / (members.size() + 1);
			members.forEach(member -> splits.add(
					Split.builder()
						.amount(splitAmount)
						.userId(member.getId())
						.build()
					));
		}
		else if (type == ExpenseSplitType.PERCENTAGE) {
			members.forEach(member -> splits.add(
					Split.builder()
						.amount(totalAmount * (memberShare.get(member.getId())) / 100 )
						.userId(member.getId())
						.build()
					));
		}
		else {
			members.forEach(member -> splits.add(
					Split.builder()
						.amount(memberShare.get(member.getId()) )
						.userId(member.getId())
						.build()
					));
		}
		return splits;
	}
}
