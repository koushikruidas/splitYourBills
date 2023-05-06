package com.projectY.splitYourBills.model;

import java.time.LocalDate;
import java.util.Map;

import com.projectY.splitYourBills.utility.ExpenseSplitType;

import lombok.Data;

@Data
public class ExpenseDTO {

    private String description;

    private double amount;

    private LocalDate date;

    private long paidById;
    
    private Map<Long, Double> memberShare;

    private long groupId;

    private ExpenseSplitType splitType;
}

