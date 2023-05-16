package com.projectY.splitYourBills.controller;

import java.util.List;

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

import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        UserDTO createUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable long id){
    	UserDTO user = userService.findById(id);
    	return ResponseEntity.ok(user);
    }
    
    @GetMapping("/get/all")
    public ResponseEntity<List<UserDTO>> findAll(){
    	List<UserDTO> users = userService.findAll();
    	return ResponseEntity.ok(users);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
    	userService.delete(id);
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDto, @PathVariable long id){
    	UserDTO user = userService.update(userDto, id);
    	return ResponseEntity.ok(user);
    }
    
}

