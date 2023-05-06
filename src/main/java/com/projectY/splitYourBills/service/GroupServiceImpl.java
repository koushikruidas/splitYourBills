package com.projectY.splitYourBills.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.projectY.splitYourBills.entity.Group;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.GroupDTO;
import com.projectY.splitYourBills.repo.GroupRepository;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GroupDTO createGroup(GroupDTO groupCreateDTO) {
        Group group = modelMapper.map(groupCreateDTO, Group.class);
        Group savedGroup = groupRepository.save(group);
        return modelMapper.map(savedGroup, GroupDTO.class);
    }

    @Override
    public GroupDTO updateGroup(Long groupId, GroupDTO groupUpdateDTO) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        modelMapper.map(groupUpdateDTO, group);
        Group savedGroup = groupRepository.save(group);
        return modelMapper.map(savedGroup, GroupDTO.class);
    }

    @Override
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        groupRepository.delete(group);
    }

    @Override
    public GroupDTO getGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }
}
