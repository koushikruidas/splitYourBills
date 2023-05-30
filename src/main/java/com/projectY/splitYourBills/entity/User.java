package com.projectY.splitYourBills.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
	
	private String name;

	@Column(unique = true)
    private String email;

	@Column(unique = true)
    private String phone;

    @OneToMany(mappedBy = "paidBy")
    private List<Expense> expenses;
    
//    @ManyToMany
//    @JoinTable(
//            name = "user_friends",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "friend_id"))
//    private List<User> friends;
//    
    @ManyToMany(mappedBy = "members")
    private List<Group> groups;

}

