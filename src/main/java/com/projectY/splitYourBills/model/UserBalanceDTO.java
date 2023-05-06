package com.projectY.splitYourBills.model;

import lombok.Data;

@Data
public class UserBalanceDTO {

    private String userName;
    private double amount;

    public UserBalanceDTO(String userName, double amount) {
        this.userName = userName;
        this.amount = amount;
    }
}
