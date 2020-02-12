package com.cdgs.temple.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationsDto {
	private Long specialApproveID;
	private Long courseID;
	private String specialApproveStatus;
	//คนที่จะแจ้งเตือน
	private Long memberID;
	private String detail;
	private Long notificationStatus;
	private Date notificationTime;
	
	public NotificationsDto() {
		super();
	}

	public NotificationsDto(Long specialApproveID, Long courseID, String specialApproveStatus, Long memberID,
			String detail, Long notificationStatus, Date notificationTime) {
		super();
		this.specialApproveID = specialApproveID;
		this.courseID = courseID;
		this.specialApproveStatus = specialApproveStatus;
		this.memberID = memberID;
		this.detail = detail;
		this.notificationStatus = notificationStatus;
		this.notificationTime = notificationTime;
	}
}
