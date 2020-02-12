package com.cdgs.temple.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MembersHasCourseDto implements Serializable {
	private static final long serialVersionUID = 8061410967659591652L;

	private long membersHasCourseId;

	private long memberId;
	private String title;
	private String fname;
	private String lname;
	private String tel;

	private long courseId;
	private char status;
	private LocalDateTime registerDate;
	private String courseName;
	private Date courseStDate;
	private Date courseEndDate;
	private long transportationId;
	private String experience;
	private String expected;
	private long senseId;
}
