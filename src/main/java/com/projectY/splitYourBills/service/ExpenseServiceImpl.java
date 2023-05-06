
package com.projectY.splitYourBills.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Expense;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.ExpenseDTO;
import com.projectY.splitYourBills.model.SplitDTO;
import com.projectY.splitYourBills.repo.ExpenseRepository;
import com.projectY.splitYourBills.utility.Split;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override 
	public ExpenseDTO createExpense(ExpenseDTO expenseDto) {
//		List<SplitDTO> splitUsers = expenseDto.getSplitDetails();
//		SplitFactory.getSplitObject(expenseDto.getSplitType());
  
		Expense expense = modelMapper.map(expenseDto, Expense.class); 
		// Map
//		SplitDtos to Splits List<Split> splitDetails = expenseDto.getSplitDetails().stream() 
//				.map(splitDto -> modelMapper.map(splitDto, Split.class))
//				.collect(Collectors.toList());
  
//		expense.setSplitDetails(splitDetails);
  
		Expense savedExpense = expenseRepository.save(expense); 
		return modelMapper.map(savedExpense, ExpenseDTO.class); 
	}

	@Override
	public ExpenseDTO getExpenseById(Long expenseId) {
		Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
		if (expenseOptional.isPresent()) {
			Expense expense = expenseOptional.get();
			return modelMapper.map(expense, ExpenseDTO.class);
		}
		throw new ResourceNotFoundException("Expense with ID " + expenseId + " not found");
	}

	@Override
	public List<ExpenseDTO> getAllExpenses() {
		List<Expense> expenses = expenseRepository.findAll();
		return expenses.stream().map(expense -> modelMapper.map(expense, ExpenseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteExpenseById(Long expenseId) {
		Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
		if (expenseOptional.isPresent()) {
			expenseRepository.deleteById(expenseId);
		} else {
			throw new ResourceNotFoundException("Expense with ID " + expenseId + " not found");
		}
	}

	@Override
	public ExpenseDTO updateExpense(Long expenseId, ExpenseDTO ExpenseDTO) {
		Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
		if (expenseOptional.isPresent()) {
			Expense expense = modelMapper.map(ExpenseDTO, Expense.class);
			Expense updatedExpense = expenseRepository.save(expense);
			return modelMapper.map(updatedExpense, ExpenseDTO.class);
		} else {
			throw new ResourceNotFoundException("Expense with ID " + expenseId + " not found");
		}
	}
}
