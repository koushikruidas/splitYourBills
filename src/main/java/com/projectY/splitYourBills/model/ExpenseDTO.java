package com.projectY.splitYourBills.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.projectY.splitYourBills.utility.ExpenseSplitType;

import lombok.Data;

@Data
public class ExpenseDTO {
	private long id;

	private long groupId;
	
    private String description;

    private double amount;

    private LocalDate date;

    private long paidById;
    
    private List<Long> memberIds;
    
    private ExpenseSplitType splitType;
    
    private Map<Long, Double> memberShare;
}

