package com.cdgs.temple.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApprovalCoursesDto implements Serializable {
	private static final long serialVersionUID = -5875499638883264049L;
	private Long id;
	private String name;
	private int conditionMin;
	private Date stDate;
	private Date endDate;
	private String detail;
	private int numberOfMembers;
}
