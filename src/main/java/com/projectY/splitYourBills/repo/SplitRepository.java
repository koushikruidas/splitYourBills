package com.projectY.splitYourBills.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projectY.splitYourBills.entity.Split;

public interface SplitRepository extends JpaRepository<Split, Long> {
	@Query(value = "SELECT * FROM split s "
			+ "WHERE s.expense_id in (:expenseIds)", nativeQuery = true)
	List<Split> findAllByExpenseId(List<Long> expenseIds);
}
