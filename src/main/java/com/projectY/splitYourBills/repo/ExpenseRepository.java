package com.projectY.splitYourBills.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projectY.splitYourBills.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	@Query(value = "SELECT * FROM expense e JOIN expense_users eu ON e.id = eu.expense_id "
			+ "WHERE eu.user_id = ?1", nativeQuery = true)
	List<Expense> findByUsers_Id(long userId);

}
