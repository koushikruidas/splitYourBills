package com.projectY.splitYourBills.service;

import java.util.List;

import com.projectY.splitYourBills.model.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO user);
	UserDTO findById(long id);
	List<UserDTO> findAll();
	void delete(long id);
	UserDTO update(UserDTO user, long id);
}
