package com.projectY.splitYourBills.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping("/create")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDto) {
        ExpenseDTO createdExpense = expenseService.createExpense(expenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable long id){
    	ExpenseDTO user = expenseService.findById(id);
    	return ResponseEntity.ok(user);
    }
    
    @GetMapping("/get/all")
    public ResponseEntity<List<ExpenseDTO>> findAll(){
    	List<ExpenseDTO> users = expenseService.findAll();
    	return ResponseEntity.ok(users);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
    	expenseService.deleteById(id);
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ExpenseDTO> update(@RequestBody ExpenseDTO ExpenseDTO, @PathVariable long id){
    	ExpenseDTO user = expenseService.update(id, ExpenseDTO);
    	return ResponseEntity.ok(user);
    }
}
