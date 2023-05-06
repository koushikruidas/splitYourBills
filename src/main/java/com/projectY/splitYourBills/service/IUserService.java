package com.projectY.splitYourBills.service;

import java.util.List;
import java.util.Optional;

import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.model.UserReqModel;

public interface IUserService {
	User add(UserReqModel user);
	Optional<User> findById(long id);
	List<User> findAll();
	void delete(long id);
	User update(User user);
}
