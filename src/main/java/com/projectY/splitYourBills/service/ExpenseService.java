package com.projectY.splitYourBills.service;

import java.util.List;

import com.projectY.splitYourBills.model.ExpenseDTO;

public interface ExpenseService {

    ExpenseDTO createExpense(ExpenseDTO ExpenseDTO);

    ExpenseDTO getExpenseById(Long expenseId);

    List<ExpenseDTO> getAllExpenses();

    void deleteExpenseById(Long expenseId);

    ExpenseDTO updateExpense(Long expenseId, ExpenseDTO ExpenseDTO);

}

