package com.projectY.splitYourBills.model;

import java.util.List;

import lombok.Data;

@Data
public class GroupDTO {
    
    private long id;

    private String name;

    private List<Long> memberIds;

    // Constructor, getters and setters
}

