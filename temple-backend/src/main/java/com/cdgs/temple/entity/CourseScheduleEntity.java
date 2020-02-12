package com.cdgs.temple.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "courses_schedule")
@IdClass(CourseScheduleEntity.class)
@Getter
@Setter
@ToString
public class CourseScheduleEntity implements Serializable {
	private static final long serialVersionUID = -470237690775417023L;

	@Id
	@Column(name = "course_id")
	private Long courseId;

	@Id
	@Column(name = "course_schedule_date")
	private Date courseScheduleDate;
}
