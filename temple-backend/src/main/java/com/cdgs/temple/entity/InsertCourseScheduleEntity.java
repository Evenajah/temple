package com.cdgs.temple.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "courses_schedule")
@IdClass(InsertCourseScheduleEntity.class)
@Getter
@Setter
@ToString
public class InsertCourseScheduleEntity implements Serializable {
	private static final long serialVersionUID = -470237690775417023L;

	@Id
	@Column(name = "course_id")
	private Long courseId;

	@Id
	@Column(name = "course_schedule_date")
	private Date courseScheduleDate;
}
