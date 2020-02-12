package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeacherHasCourseDto implements Serializable {
	private static final long serialVersionUID = 7246888756440534505L;

	private long courseId;
	private String name;
}