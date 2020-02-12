package com.cdgs.temple.dto;

import com.cdgs.temple.entity.CourseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class CourseScheduleDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2152388262292088237L;

	private Long courseId;
	private Date courseScheduleDate;
	private CourseEntity course;
	private int student;
}
