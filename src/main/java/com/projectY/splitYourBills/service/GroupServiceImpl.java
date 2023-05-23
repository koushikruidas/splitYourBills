package com.projectY.splitYourBills.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Group;
import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.AddMemberReqDTO;
import com.projectY.splitYourBills.model.GroupDTO;
import com.projectY.splitYourBills.repo.GroupRepository;
import com.projectY.splitYourBills.repo.UserRepository;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper mapper
    		, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public GroupDTO createGroup(GroupDTO groupDto) {
    	List<User> memberList = userRepository.findAllByIds(groupDto.getMemberIds());
        Group group = Group.builder()
                .name(groupDto.getName())
                .members(memberList)
                .expenses(new ArrayList<>())
                .build();
        groupRepository.save(group);
        GroupDTO response = GroupDTO.builder()
        		.name(group.getName())
        		.id(group.getId())
        		.memberIds(groupDto.getMemberIds())
        		.build();
        return response;
    }

    @Override
    public GroupDTO updateGroup(Long groupId, GroupDTO groupDto) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        List<User> memberList = userRepository.findAllByIds(groupDto.getMemberIds());
        if(!groupDto.getName().isBlank()) {
        	group.setName(groupDto.getName());
        }
        if(!groupDto.getMemberIds().isEmpty()) {
        	group.setMembers(memberList);
        }
        Group savedGroup = groupRepository.save(group);
        return mapper.map(savedGroup, GroupDTO.class);
    }

    @Override
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        groupRepository.delete(group);
    }

    @Override
    public GroupDTO getGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        List<Long> memberIds = group.getMembers().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return GroupDTO.builder()
        		.name(group.getName())
        		.id(group.getId())
        		.memberIds(memberIds)
        		.build();
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> mapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }

	@Override
	public GroupDTO addMembers(AddMemberReqDTO members) {
		List<User> memberList = userRepository.findAllByIds(members.getMemberIds());
		Group group = groupRepository.findById(members.getGroupId())
				.orElseThrow(
						() -> new ResourceNotFoundException("Group not found","id :",members.getGroupId())
				);
		group.getMembers().addAll(memberList);
		groupRepository.save(group);
		return mapper.map(group, GroupDTO.class);
	}
}
