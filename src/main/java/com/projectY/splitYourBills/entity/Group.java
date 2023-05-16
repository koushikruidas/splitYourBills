package com.projectY.splitYourBills.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_group")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Default
    private List<User> members = new ArrayList<User>();
    
    @OneToMany(mappedBy = "group")
    @Default
    private List<Expense> expenses = new ArrayList<Expense>();

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name +"]";
	}
}

