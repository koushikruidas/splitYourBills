package com.projectY.splitYourBills.model;

import java.util.List;

import lombok.Data;

@Data
public class AddMemberReq {
	private long groupId;
	private List<Long> memberIds;
}
