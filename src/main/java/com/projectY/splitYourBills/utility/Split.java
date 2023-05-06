package com.projectY.splitYourBills.utility;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.projectY.splitYourBills.entity.Expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Split {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    private long userId;
    private Double amount;
    @ManyToOne
    @JoinColumn(name="expense_id")
    private Expense expense;
}
