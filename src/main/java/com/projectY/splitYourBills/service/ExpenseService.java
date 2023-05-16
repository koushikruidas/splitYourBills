package com.projectY.splitYourBills.service;

import java.util.List;

import com.projectY.splitYourBills.model.ExpenseDTO;

public interface ExpenseService {

    ExpenseDTO createExpense(ExpenseDTO ExpenseDTO);

    ExpenseDTO findById(Long expenseId);

    List<ExpenseDTO> findAll();

    void deleteById(Long expenseId);

    ExpenseDTO update(Long expenseId, ExpenseDTO ExpenseDTO);

}

