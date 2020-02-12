package com.cdgs.temple.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name = "members_has_courses")
@Embeddable
@Getter
@Setter
@ToString
public class MembersHasCourseEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5345044781843738399L;

	@Id
	@Column(name = "members_has_course_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long membersHasCourseId;

	@Column(name = "member_id")
	private long memberId;

	@Column(name = "course_id")
	private long courseId;

	@Column(name = "mhc_status")
	private char mhcStatus;

	@Column(name = "register_date")
	@CreationTimestamp
	private LocalDateTime registerDate;
	
	@Column(name = "sense_id")
	private long senseId;

	@ManyToOne
	@JoinColumn(name = "tran_id")
	private TransportationEntity tranId;

	@OneToOne
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private CourseEntity course;

	@OneToOne
	@JoinColumn(name = "sense_id", insertable = false, updatable = false)
	private SensationEntity sense;
}
