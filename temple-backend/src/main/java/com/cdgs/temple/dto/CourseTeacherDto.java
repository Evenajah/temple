package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseTeacherDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -343434631679054967L;

	private Long courseId;
	private Long memberId;
}
