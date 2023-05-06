package com.projectY.splitYourBills.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Expense;
import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.UserBalanceDTO;
import com.projectY.splitYourBills.repo.ExpenseRepository;
import com.projectY.splitYourBills.repo.UserRepository;

@Service
public class BalanceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<UserBalanceDTO> getUserBalances(long userId) {
        List<Expense> expenses = expenseRepository.findByUsers_Id(userId);
        List<UserBalanceDTO> userBalances = new ArrayList<>();

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        for (Expense expense : expenses) {
            for (User userInExpense : expense.getUsers()) {
                if (userInExpense.getId() != userId) {
                    double amount = expense.getAmount() / expense.getUsers().size();
                    UserBalanceDTO userBalance = new UserBalanceDTO(userInExpense.getName(), amount);
                    if (userBalances.contains(userBalance)) {
                        userBalances.get(userBalances.indexOf(userBalance)).setAmount(
                                userBalances.get(userBalances.indexOf(userBalance)).getAmount() + amount);
                    } else {
                        userBalances.add(userBalance);
                    }
                }
            }
        }

//        List<User> friends = user.getFriends();
//        for (User friend : friends) {
//            if (friend.getId() != userId) {
//                double amount = 0.0;
//                for (UserBalanceDTO userBalanceDTO : userBalances) {
//                    if (userBalanceDTO.getUserName().equals(friend.getName())) {
//                        amount = userBalanceDTO.getAmount();
//                    }
//                }
//                UserBalanceDTO userBalance = new UserBalanceDTO(friend.getName(), amount * -1);
//                userBalances.add(userBalance);
//            }
//        }

        return userBalances;
    }
}

