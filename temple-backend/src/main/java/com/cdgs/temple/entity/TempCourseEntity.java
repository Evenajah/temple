package com.cdgs.temple.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "courses")
@Embeddable
@Getter
@Setter
@ToString
public class TempCourseEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1615306482250637476L;

	@Id
	@Column(name = "course_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;

	@Column(name = "course_no")
	private Long courseNo;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "st_date")
	private Date stDate;

	@Column(name = "course_detail")
	private String courseDetail;

	@Column(name = "course_condition_min")
	private int courseConditionMin;

	@Column(name = "course_location_id")
	private Long courseLocationId;

	@Column(name = "course_create_by")
	private Long courseCreateBy;

	@Column(name = "course_create_date")
	@CreationTimestamp
	private Date courseCreateDate;

	@Column(name = "course_last_update")
	@CreationTimestamp
	private Date courseLastUpdate;

	@ManyToOne
	@JoinColumn(name = "course_create_by", insertable = false, updatable = false)
	private MemberEntity createBy;

	@ManyToOne
	@JoinColumn(name = "course_location_id", insertable = false, updatable = false)
	private LocationEntity locationId;

	private String mhcStatus;
	private String saStatus;
	private String statusText;
	private int canRegister;
}
