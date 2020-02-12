package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberGraduated implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4572932467463397671L;
	private long memberId;
	private String fname;
	private String lname;
	private long courseId;
	private String status;
}
