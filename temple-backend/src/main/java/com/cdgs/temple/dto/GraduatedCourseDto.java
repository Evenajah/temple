package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GraduatedCourseDto implements Serializable {
	private static final long serialVersionUID = 111852761610608931L;
	private Long id;
	private String name;
	private String detail;
	private Long numberOfMembers;
}
