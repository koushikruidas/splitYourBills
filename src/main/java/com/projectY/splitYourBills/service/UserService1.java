package com.projectY.splitYourBills.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Expense;
import com.projectY.splitYourBills.entity.Group;
import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.model.ExpenseDTO;
import com.projectY.splitYourBills.model.FriendDTO;
import com.projectY.splitYourBills.model.GroupDTO;
import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.repo.ExpenseRepository;
import com.projectY.splitYourBills.repo.GroupRepository;
import com.projectY.splitYourBills.repo.UserRepository;
import com.projectY.splitYourBills.utility.ExpenseSplitType;
import com.projectY.splitYourBills.utility.Split;

@Service
public class UserService1 {

	private final UserRepository userRepository;
	private final GroupRepository groupRepository;
	private final ExpenseRepository expenseRepository;
	private ModelMapper mapper;

	public UserService1(UserRepository userRepository, GroupRepository groupRepository,
			ExpenseRepository expenseRepository, ModelMapper mapper) {
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
		this.expenseRepository = expenseRepository;
		this.mapper = mapper;
	}

	@Transactional
	public User createUser(UserDTO userDto) {
		User user = mapper.map(userDto, User.class);
		return userRepository.save(user);
	}

	@Transactional
    public Group createGroup(GroupDTO groupDTO) {
        Group group = Group.builder()
                .name(groupDTO.getName())
                .members(
                		groupDTO.getMemberIds().stream()
                		.map(i -> userRepository.findById(i).get())
                		.collect(Collectors.toList())
                		)
                .expenses(new ArrayList<>())
                .build();
        groupRepository.save(group);
        for (Long memberId : groupDTO.getMemberIds()) {
            Optional<User> optionalUser = userRepository.findById(memberId);
            if (optionalUser.isPresent()) {
                User member = optionalUser.get();
                group.getMembers().add(member);
                member.getGroups().add(group);
            }
        }
        System.out.println(group.toString());
        groupRepository.save(group);
        return group;
    }

	@Transactional
	public void addFriend(FriendDTO friendDTO) {
		Optional<User> optionalUser = userRepository.findById(friendDTO.getFriendId());
		Optional<User> optionalFriend = userRepository.findById(friendDTO.getFriendId());
		if (optionalUser.isPresent() && optionalFriend.isPresent()) {
			User user = optionalUser.get();
			User friend = optionalFriend.get();
//            user.getFriends().add(friend);
//            friend.getFriends().add(user);
			userRepository.save(user);
			userRepository.save(friend);
		}
	}

	@Transactional
	public void addExpense(ExpenseDTO expenseDTO) {
		Optional<Group> optionalGroup = groupRepository.findById(expenseDTO.getGroupId());
		if (optionalGroup.isPresent()) {
			Group group = optionalGroup.get();
			Expense expense = Expense.builder().group(group).description(expenseDTO.getDescription())
					.amount(expenseDTO.getAmount()).date(expenseDTO.getDate())
					.paidBy(userRepository.findById(expenseDTO.getPaidById()).get()).users(group.getMembers())
					.splitType(ExpenseSplitType.EQUAL).splitDetails(UserService1.split(expenseDTO.getSplitType(),
							expenseDTO.getAmount(), group.getMembers(), expenseDTO.getMemberShare()))
					.build();
			expenseRepository.save(expense);
		}
	}

	public static List<Split> split(ExpenseSplitType type, double totalAmount, List<User> members,
			Map<Long, Double> memberShare) {
		List<Split> splits = new ArrayList<>();
		Double splitAmount = totalAmount / members.size();
		members.forEach(member -> splits.add(Split.builder().amount(splitAmount).userId(member.getId()).build()));
		return splits;
	}
}
