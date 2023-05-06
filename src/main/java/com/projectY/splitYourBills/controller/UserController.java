/*
 * package com.projectY.splitYourBills.controller;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.DeleteMapping; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.PutMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.projectY.splitYourBills.entity.User; import
 * com.projectY.splitYourBills.exception.ResourceNotFoundException; import
 * com.projectY.splitYourBills.model.UserReqModel; import
 * com.projectY.splitYourBills.service.UserService;
 * 
 * @RestController
 * 
 * @RequestMapping("/users") public class UserController {
 * 
 * @Autowired private UserService userService;
 * 
 * @PostMapping("") public ResponseEntity<User> createUser(@RequestBody
 * UserReqModel userReq) { User savedUser = userService.add(userReq); return new
 * ResponseEntity<>(savedUser, HttpStatus.CREATED); }
 * 
 * @GetMapping("/{userId}") public ResponseEntity<User>
 * getUserById(@PathVariable Long userId) { User user =
 * userService.findById(userId) .orElseThrow(() -> new
 * ResourceNotFoundException("User","Id",userId)); return new
 * ResponseEntity<>(user, HttpStatus.OK); }
 * 
 * @PutMapping("/{userId}") public ResponseEntity<User> updateUser(@PathVariable
 * Long userId, @RequestBody User user) { User existingUser =
 * userService.findById(userId) .orElseThrow(() -> new
 * ResourceNotFoundException("User","Id",userId));
 * existingUser.setUserName(user.getUserName());
 * existingUser.setUserExpenseBalanceSheet(user.getUserExpenseBalanceSheet());
 * User updatedUser = userService.update(existingUser); return new
 * ResponseEntity<>(updatedUser, HttpStatus.OK); }
 * 
 * @DeleteMapping("/{userId}") public ResponseEntity<Void>
 * deleteUser(@PathVariable Long userId) { userService.delete(userId); return
 * new ResponseEntity<>(HttpStatus.NO_CONTENT); } }
 */