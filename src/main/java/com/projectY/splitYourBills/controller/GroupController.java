package com.projectY.splitYourBills.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectY.splitYourBills.model.AddMemberReqDTO;
import com.projectY.splitYourBills.model.GroupDTO;
import com.projectY.splitYourBills.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupCreateDTO) {
        GroupDTO createdGroup = groupService.createGroup(groupCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        GroupDTO groupDTO = groupService.getGroupById(id);
        return ResponseEntity.ok(groupDTO);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<GroupDTO> updateGroupById(@PathVariable Long id, @RequestBody GroupDTO groupDto) {
        GroupDTO updatedGroup = groupService.updateGroup(id,groupDto);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/get/all")
    public ResponseEntity<List<GroupDTO>> getAll(){
    	List<GroupDTO> allGroups = groupService.getAllGroups();
    	return ResponseEntity.status(HttpStatus.OK).body(allGroups);
    }
    
    @PutMapping("/addMembers")
    public ResponseEntity<GroupDTO> addMembers(@RequestBody AddMemberReqDTO members){
    	GroupDTO addMembers = groupService.addMembers(members);
    	return ResponseEntity.ok(addMembers);
    }
    
}

