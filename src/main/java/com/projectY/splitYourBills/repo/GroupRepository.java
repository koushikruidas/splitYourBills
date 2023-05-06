package com.projectY.splitYourBills.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectY.splitYourBills.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
