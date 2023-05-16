package com.projectY.splitYourBills.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectY.splitYourBills.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "SELECT * FROM t_user WHERE id IN :ids", nativeQuery = true)
	List<User> findAllByIds(@Param("ids") List<Long> ids);
}
