package com.projectY.splitYourBills.model;

import lombok.Data;

@Data
public class BalanceDTO {

    private long userId;
    private double amount;

    public BalanceDTO(long userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }
}

