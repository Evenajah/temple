package com.cdgs.temple.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @created 13/01/2563
 * @author PRAYOON SUNTORNKITI
 *
 */
@SqlResultSetMapping(name = "findAllCourseEntityDataMapping", classes = {
		@ConstructorResult(targetClass = CourseEntity.class, columns = {
				@ColumnResult(name = "courseId", type = Long.class),
				@ColumnResult(name = "courseNo", type = Long.class),
				@ColumnResult(name = "courseName", type = String.class),
				@ColumnResult(name = "courseStDate", type = Timestamp.class),
				@ColumnResult(name = "courseEndDate", type = Timestamp.class),
				@ColumnResult(name = "courseDetail", type = String.class),
				@ColumnResult(name = "courseConditionMin", type = Integer.class),
				@ColumnResult(name = "courseLocationId", type = Long.class),
				@ColumnResult(name = "courseCreateBy", type = Long.class),
				@ColumnResult(name = "courseCreateDate", type = Timestamp.class),
				@ColumnResult(name = "courseLastUpdate", type = Timestamp.class),
				@ColumnResult(name = "courseStatus", type = String.class),
				@ColumnResult(name = "courseEnable", type = Boolean.class),
				@ColumnResult(name = "locationName", type = String.class),
				@ColumnResult(name = "transportationId", type = Long.class) }) })

@NamedNativeQuery(name = "findAllCourseEntity", resultSetMapping = "findAllCourseEntityDataMapping", query = "SELECT c.course_id AS courseId, c.course_no AS courseNo, c.course_name AS courseName, "
		+ "c.course_st_date AS courseStDate, c.course_end_date AS courseEndDate, c.course_detail AS courseDetail, "
		+ "c.course_condition_min AS courseConditionMin , c.course_location_id AS courseLocationId, c.course_create_by AS courseCreateBy, "
		+ "c.course_create_date AS courseCreateDate, c.course_last_update AS courseLastUpdate, c.course_status AS courseStatus, "
		+ "c.course_enable AS courseEnable, l.location_name AS locationName, t.tran_id AS transportationId "
		+ "FROM courses c " + "LEFT JOIN locations l ON c.course_location_id = l.location_id "
		+ "LEFT JOIN transportations t ON c.course_id = t.course_id "
		+ "LEFT JOIN transportations_time tt ON t.tran_time_id = tt.tran_time_id ")

@Entity
@Table(name = "courses")
@Embeddable
@Getter
@Setter
@ToString
public class CourseEntity implements Serializable {

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

	@Column(name = "course_st_date")
	private Date courseStDate;

	@Column(name = "course_end_date")
	private Date courseEndDate;

	@Column(name = "course_detail")
	private String courseDetail;

	@Column(name = "course_condition_min")
	private Integer courseConditionMin;

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

	@Column(name = "course_status")
	private String courseStatus;

	@Column(name = "course_enable")
	private Boolean courseEnable = true;

	@ManyToOne
	@JoinColumn(name = "course_create_by", insertable = false, updatable = false)
	private MemberEntity createBy;

	@ManyToOne
	@JoinColumn(name = "course_location_id", insertable = false, updatable = false)
	private LocationEntity locationId;

	@Transient
	private Long transportationId;

	@Transient
	private String locationName;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private List<CourseScheduleEntity> courseSchdule;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private List<CourseTeacherEntity> courseTeacher;
	
	public CourseEntity() {
		super();
	}

	public CourseEntity(Long courseId, Long courseNo, String courseName, Date courseStDate, Date courseEndDate,
			String courseDetail, Integer courseConditionMin, Long courseLocationId, Long courseCreateBy,
			Date courseCreateDate, Date courseLastUpdate, String courseStatus, Boolean courseEnable,
			String locationName, Long transportationId) {
		this.courseId = courseId;
		this.courseNo = courseNo;
		this.courseName = courseName;
		this.courseStDate = courseStDate;
		this.courseEndDate = courseEndDate;
		this.courseDetail = courseDetail;
		this.courseConditionMin = courseConditionMin;
		this.courseLocationId = courseLocationId;
		this.courseCreateBy = courseCreateBy;
		this.courseCreateDate = courseCreateDate;
		this.courseLastUpdate = courseLastUpdate;
		this.courseStatus = courseStatus;
		this.courseEnable = courseEnable;
		this.locationName = locationName;
		this.transportationId = transportationId;

	}
}
