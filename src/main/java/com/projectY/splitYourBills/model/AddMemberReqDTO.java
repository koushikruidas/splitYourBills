package com.projectY.splitYourBills.model;

import java.util.List;

import lombok.Data;

@Data
public class AddMemberReqDTO {
	private long groupId;
	private List<Long> memberIds;
}
