package com.projectY.splitYourBills.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    
    private long id;

    private String name;

    private List<Long> memberIds;
    
}

