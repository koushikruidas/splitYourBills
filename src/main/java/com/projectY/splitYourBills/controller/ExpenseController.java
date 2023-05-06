package com.projectY.splitYourBills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectY.splitYourBills.model.ExpenseDTO;
import com.projectY.splitYourBills.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDto) {
        // Create expense
        ExpenseDTO createdExpense = expenseService.createExpense(expenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }
}
