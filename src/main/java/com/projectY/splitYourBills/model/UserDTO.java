package com.projectY.splitYourBills.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private long id;
	
	private String name;

    private String email;

    private String phone;
}
