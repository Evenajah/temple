package com.cdgs.temple.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class GraduatedCourseEntity implements Serializable {
	private static final long serialVersionUID = 128133865181964921L;

	@Id
	private Long courseId;
	private String courseName;
	private Long countMember;
	private String courseDetail;
}
