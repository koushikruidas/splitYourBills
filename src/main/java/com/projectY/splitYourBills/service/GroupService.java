package com.projectY.splitYourBills.service;

import java.util.List;

import com.projectY.splitYourBills.model.AddMemberReqDTO;
import com.projectY.splitYourBills.model.GroupDTO;
import com.projectY.splitYourBills.model.UserDTO;

public interface GroupService {
    
    GroupDTO createGroup(GroupDTO groupCreateDTO);
    
    GroupDTO updateGroup(Long id, GroupDTO groupUpdateDTO);
    
    GroupDTO getGroupById(Long id);
    
    List<GroupDTO> getAllGroups();
    
    void deleteGroup(Long id);
    
    GroupDTO addMembers(AddMemberReqDTO members);
    
    List<UserDTO> getMembers(long id);
}

