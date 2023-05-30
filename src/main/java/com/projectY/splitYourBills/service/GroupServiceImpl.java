package com.projectY.splitYourBills.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Group;
import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.AddMemberReqDTO;
import com.projectY.splitYourBills.model.GroupDTO;
import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.repo.GroupRepository;
import com.projectY.splitYourBills.repo.UserRepository;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	private final ModelMapper mapper;

	public GroupServiceImpl(GroupRepository groupRepository, ModelMapper mapper, UserRepository userRepository) {
		this.groupRepository = groupRepository;
		this.mapper = mapper;
		this.userRepository = userRepository;
	}

	@Transactional
	@Override
	public GroupDTO createGroup(GroupDTO groupDto) {
		Group group = null;
		if (groupDto.getMembersEmailOrPhone().size() > 0) {
			List<User> memberList = userRepository.findByEmailOrPhone(groupDto.getMembersEmailOrPhone());
			group = Group.builder().name(groupDto.getName()).members(memberList).expenses(new ArrayList<>()).build();
		} else {

			group = Group.builder().name(groupDto.getName()).expenses(new ArrayList<>()).build();
		}
		groupRepository.save(group);
		GroupDTO response = GroupDTO.builder().id(group.getId()).name(group.getName()).membersEmailOrPhone(
				group.getMembers().stream().filter(i -> i.getEmail() != null || i.getPhone() != null).flatMap(user -> {
					if (user.getEmail() != null) {
						return Stream.of(user.getEmail());
					} else {
						return Stream.of(user.getPhone());
					}
				}).collect(Collectors.toList())).build();
		return response;
	}

	@Override
	public GroupDTO updateGroup(Long groupId, GroupDTO groupDto) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Group not found"));
		List<User> memberList = userRepository.findByEmailOrPhone(groupDto.getMembersEmailOrPhone());
		if (!groupDto.getName().isBlank()) {
			group.setName(groupDto.getName());
		}
		if (!groupDto.getMembersEmailOrPhone().isEmpty()) {
			group.setMembers(memberList);
		}
		Group savedGroup = groupRepository.save(group);
		GroupDTO response = GroupDTO.builder().id(savedGroup.getId()).name(savedGroup.getName()).membersEmailOrPhone(
				savedGroup.getMembers().stream().filter(i -> i.getEmail() != null || i.getPhone() != null).flatMap(user -> {
					if (user.getEmail() != null) {
						return Stream.of(user.getEmail());
					} else {
						return Stream.of(user.getPhone());
					}
				}).collect(Collectors.toList())).build();
		return response;
	}

	@Override
	public void deleteGroup(Long groupId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Group not found"));
		groupRepository.delete(group);
	}

	@Override
	public GroupDTO getGroupById(Long groupId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Group not found"));
		List<String> membersEmailOrPhone = group.getMembers().stream()
				.filter(i -> i.getEmail() != null || i.getPhone() != null).flatMap(user -> {
					if (user.getEmail() != null) {
						return Stream.of(user.getEmail());
					} else {
						return Stream.of(user.getPhone());
					}
				}).collect(Collectors.toList());
		return GroupDTO.builder().name(group.getName()).id(group.getId()).membersEmailOrPhone(membersEmailOrPhone).build();
	}

	@Override
	public List<GroupDTO> getAllGroups() {
		List<Group> groups = groupRepository.findAll();
		return groups.stream().map(group -> mapper.map(group, GroupDTO.class)).collect(Collectors.toList());
	}

	@Override
	public GroupDTO addMembers(AddMemberReqDTO members) {
		List<User> memberList = userRepository.findAllByIds(members.getMemberIds());
		Group group = groupRepository.findById(members.getGroupId())
				.orElseThrow(() -> new ResourceNotFoundException("Group not found", "id :", members.getGroupId()));
		group.getMembers().addAll(memberList);
		groupRepository.save(group);
		return mapper.map(group, GroupDTO.class);
	}

	@Override
	public List<UserDTO> getMembers(long id) {
		Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
		List<UserDTO> members = group.getMembers().stream().map(i -> mapper.map(i, UserDTO.class))
				.collect(Collectors.toList());
		return members;
	}
}
