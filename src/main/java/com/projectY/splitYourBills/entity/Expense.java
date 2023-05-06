package com.projectY.splitYourBills.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.projectY.splitYourBills.utility.ExpenseSplitType;
import com.projectY.splitYourBills.utility.Split;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
	
    private String description;
    
    private double amount;
    
    private LocalDate date;
   
    @ManyToOne
    private User paidBy;

    @ManyToMany
    @JoinTable(
            name = "expense_users",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_group_id")
    private Group group;
    
    private ExpenseSplitType splitType;
    
    @OneToMany(mappedBy = "expense" ,fetch = FetchType.LAZY)
    @Default
    private List<Split> splitDetails = new ArrayList<>();

}