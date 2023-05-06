package com.projectY.splitYourBills.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectY.splitYourBills.entity.Group;
import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.model.ExpenseDTO;
import com.projectY.splitYourBills.model.FriendDTO;
import com.projectY.splitYourBills.model.GroupDTO;
import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.service.UserService1;

@RestController
@RequestMapping("/users")
public class UserController1 {
    
    private final UserService1 userService;
    
    public UserController1(UserService1 userService) {
        this.userService = userService;
    }
    
    @PostMapping("/createUser")
    public User createGroup(@RequestBody UserDTO userDto) {
        return userService.createUser(userDto);
    }
    
    @PostMapping("/createGroup")
    public Group createGroup(@RequestBody GroupDTO groupDTO) {
        return userService.createGroup(groupDTO);
    }
    
    @PostMapping("/addFriend")
    public void addFriend(@RequestBody FriendDTO friendDTO) {
        userService.addFriend(friendDTO);
    }
    
    @PostMapping("/addExpense")
    public void addExpense(@RequestBody ExpenseDTO expenseDTO) {
        userService.addExpense(expenseDTO);
    }
}

