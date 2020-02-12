package com.cdgs.temple.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "courses_teacher")
@IdClass(CourseTeacherEntity.class)
@Getter
@Setter
@ToString
public class CourseTeacherEntity implements Serializable {
	private static final long serialVersionUID = 8792828341834437997L;

	@Id
	@Column(name = "course_id")
	private Long courseId;

	@Id
	@Column(name = "member_id")
	private Long memberId;
}
