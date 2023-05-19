package com.projectY.splitYourBills.model;

import com.projectY.splitYourBills.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private User fromUser;
    private double amount;
    private User toUser;
}
