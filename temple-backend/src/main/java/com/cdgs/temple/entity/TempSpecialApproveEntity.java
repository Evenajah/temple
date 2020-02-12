package com.cdgs.temple.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SqlResultSetMapping(name = "findTempSpecialApproveOuttimeEntityDataMapping", classes = {
		@ConstructorResult(targetClass = TempSpecialApproveEntity.class, columns = {
				@ColumnResult(name = "specialApproveId", type = Long.class),
				@ColumnResult(name = "memberId", type = Long.class),
				@ColumnResult(name = "spaDetail", type = String.class),
				@ColumnResult(name = "displayName", type = String.class),
				@ColumnResult(name = "courseId", type = Long.class),
				@ColumnResult(name = "courseName", type = String.class),
				@ColumnResult(name = "courseDetail", type = String.class),
				@ColumnResult(name = "courseStDate", type = java.sql.Date.class),
				@ColumnResult(name = "courseEndDate", type = java.sql.Date.class),
				@ColumnResult(name = "transportation", type = String.class), }) })

@NamedNativeQuery(name = "findTempSpecialApproveOuttimeEntity", resultSetMapping = "findTempSpecialApproveOuttimeEntityDataMapping", query = "SELECT sa.special_approve_id AS specialApproveId, "
		+ "sa.member_id AS memberId, sa.spa_detail AS spaDetail, "
		+ "CONCAT(t.title_name,m.member_fname,' ',m.member_lname) AS displayName, "
		+ "c.course_id AS courseId, c.course_name AS courseName, c.course_detail AS courseDetail, "
		+ "c.course_st_date AS courseStDate, c.course_end_date AS courseEndDate, " + "ts.tran_name AS transportation "
		+ "FROM special_approve sa "
		+ "LEFT JOIN courses_teacher ct ON ct.course_id = sa.course_id AND ct.member_id =:memberId "
		+ "LEFT JOIN members m ON m.member_id = sa.member_id "
		+ "LEFT JOIN title_names t ON m.member_title_id=t.title_id "
		+ "LEFT JOIN courses c ON c.course_id = sa.course_id "
		+ "INNER JOIN transportations ts on ts.tran_id=sa.tran_id "
		+ "WHERE sa.spa_status = '4' AND sa.course_id =:courseId ")

@Entity
@Getter
@Setter
@ToString
public class TempSpecialApproveEntity implements Serializable {
	private static final Long serialVersionUID = -4780915762853262556L;

	@Id
	private Long specialApproveId;
	private Long memberId;
	private String spaDetail;
	private String displayName;
	
	@Transient
	private Long courseId;

	@Transient
	private String courseName;

	@Transient
	private String courseDetail;

	@Transient
	@Temporal(TemporalType.DATE)
	private Date courseStDate;

	@Transient
	@Temporal(TemporalType.DATE)
	private Date courseEndDate;
	private String transportation;
	
	public TempSpecialApproveEntity() {
		super();
	}

	public TempSpecialApproveEntity(Long specialApproveId, Long memberId, String spaDetail, String displayName,
			Long courseId, String courseName, String courseDetail, Date courseStDate, Date courseEndDate,
			String transportation) {
		super();
		this.specialApproveId = specialApproveId;
		this.memberId = memberId;
		this.spaDetail = spaDetail;
		this.displayName = displayName;
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDetail = courseDetail;
		this.courseStDate = courseStDate;
		this.courseEndDate = courseEndDate;
		this.transportation = transportation;
	}
}
