package com.cdgs.temple.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LockerDto implements Serializable {
	private static final long serialVersionUID = -4621882744690940670L;

	private Long lockerId;
	private Long locationId;
	private String number;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdate;
	private char isActive;
	private boolean enable;
	private String locationName;
	private Long createBy;
}
