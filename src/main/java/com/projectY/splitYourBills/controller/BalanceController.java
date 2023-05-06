package com.projectY.splitYourBills.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectY.splitYourBills.model.UserBalanceDTO;
import com.projectY.splitYourBills.service.BalanceService;

@RestController
@RequestMapping("/api")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/users/{userId}/balances")
    public ResponseEntity<List<UserBalanceDTO>> getUserBalances(@PathVariable long userId) {
        List<UserBalanceDTO> userBalances = balanceService.getUserBalances(userId);
        return ResponseEntity.ok().body(userBalances);
    }
}

