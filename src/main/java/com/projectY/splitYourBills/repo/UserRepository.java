package com.projectY.splitYourBills.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectY.splitYourBills.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
