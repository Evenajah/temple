package com.cdgs.temple.entity;

import java.io.Serializable;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SqlResultSetMapping(name = "getCourseNameMapping", classes = {
		@ConstructorResult(targetClass = ReportGenEntity.class, columns = {
				@ColumnResult(name = "courseId", type = Long.class),
				@ColumnResult(name = "courseName", type = String.class) }) })

@NamedNativeQuery(name = "findCourseName", resultSetMapping = "getCourseNameMapping", query = "SELECT "
		+ "c.course_id AS courseId, c.course_name AS courseName " + "FROM members_has_courses mhc "
		+ "LEFT JOIN courses c " + "ON mhc.course_id = c.course_id " + "GROUP BY c.course_id")

@SqlResultSetMapping(name = "getDataReportDataMapping", classes = {
		@ConstructorResult(targetClass = ReportGenEntity.class, columns = {
				@ColumnResult(name = "courseId", type = Long.class),
				@ColumnResult(name = "courseName", type = String.class),
				@ColumnResult(name = "genderM", type = Long.class), @ColumnResult(name = "genderF", type = Long.class),
				@ColumnResult(name = "genderOther", type = Long.class),
				@ColumnResult(name = "transSelf", type = Long.class),
				@ColumnResult(name = "transTemple", type = Long.class),
				@ColumnResult(name = "newStudent", type = Long.class),
				@ColumnResult(name = "bangkok", type = Long.class), @ColumnResult(name = "center", type = Long.class),
				@ColumnResult(name = "sakonnakhon", type = Long.class),
				@ColumnResult(name = "northeast", type = Long.class), @ColumnResult(name = "north", type = Long.class),
				@ColumnResult(name = "east", type = Long.class), @ColumnResult(name = "west", type = Long.class),
				@ColumnResult(name = "south", type = Long.class) }) })

@NamedNativeQuery(name = "getAllDataReport", resultSetMapping = "getDataReportDataMapping", query = "SELECT "
		+ "c.course_id AS courseId, c.course_name AS courseName, "
		+ "COUNT(CASE WHEN g.gender_id = '1' THEN 1 ELSE NULL END) AS genderM, "
		+ "COUNT(CASE WHEN g.gender_id = '2' THEN 1 ELSE NULL END) AS genderF, "
		+ "COUNT(CASE WHEN g.gender_id = '3' THEN 1 ELSE NULL END) AS genderOther, "
		+ "COUNT(CASE WHEN t.tran_time_id is Null THEN 1 ELSE NULL END) AS transSelf, "
		+ "COUNT(CASE WHEN t.tran_time_id is NOT Null THEN 1 ELSE NULL END) AS transTemple, "
		+ "COUNT(CASE WHEN (SELECT COUNT(*) FROM members_has_courses mhcs WHERE mhcs.mhc_status IN ('0','1') "
		+ "AND mhcs.member_id = mhc.member_id) > 0 THEN NULL ELSE 1 END) as newStudent, "
		+ "COUNT(CASE WHEN p.province_id = 69 AND r.region_id = '4' THEN 1 ELSE NULL END) as bangkok, "
		+ "COUNT(CASE WHEN p.province_id != 69 AND r.region_id = '4' THEN 1 ELSE NULL END) as center, "
		+ "COUNT(CASE WHEN r.region_id = '2' AND p.province_id = '19' THEN 1 ELSE NULL END) as sakonnakhon, "
		+ "COUNT(CASE WHEN r.region_id = '2' AND p.province_id != '19' THEN 1 ELSE NULL END) as northeast, "
		+ "COUNT(CASE WHEN r.region_id = '1' THEN 1 ELSE NULL END) as north, "
		+ "COUNT(CASE WHEN r.region_id = '5' THEN 1 ELSE NULL END) as east, "
		+ "COUNT(CASE WHEN r.region_id = '3' THEN 1 ELSE NULL END) as west, "
		+ "COUNT(CASE WHEN r.region_id = '6' THEN 1 ELSE NULL END) as south " + "FROM members_has_courses mhc\r\n"
		+ "LEFT JOIN members m ON mhc.member_id = m.member_id " + "LEFT JOIN courses c ON mhc.course_id = c.course_id "
		+ "LEFT JOIN gender g ON m.member_gender_id = g.gender_id "
		+ "LEFT JOIN transportations t ON mhc.tran_id = t.tran_id "
		+ "LEFT JOIN province p ON m.member_province_id = p.province_id "
		+ "LEFT JOIN region r ON p.region_id = r.region_id " + "WHERE 1=1 " + "GROUP BY c.course_id")

@NamedNativeQuery(name = "getDataReportByCourseId", resultSetMapping = "getDataReportDataMapping", query = "SELECT "
		+ "c.course_id AS courseId, c.course_name AS courseName, "
		+ "COUNT(CASE WHEN g.gender_id = '1' THEN 1 ELSE NULL END) AS genderM, "
		+ "COUNT(CASE WHEN g.gender_id = '2' THEN 1 ELSE NULL END) AS genderF, "
		+ "COUNT(CASE WHEN g.gender_id = '3' THEN 1 ELSE NULL END) AS genderOther, "
		+ "COUNT(CASE WHEN t.tran_time_id is Null THEN 1 ELSE NULL END) AS transSelf, "
		+ "COUNT(CASE WHEN t.tran_time_id is NOT Null THEN 1 ELSE NULL END) AS transTemple, "
		+ "COUNT(CASE WHEN (SELECT COUNT(*) FROM members_has_courses mhcs WHERE mhcs.mhc_status IN ('0','1') "
		+ "AND mhcs.member_id = mhc.member_id) > 0 THEN NULL ELSE 1 END) as newStudent, "
		+ "COUNT(CASE WHEN p.province_id = 69 AND r.region_id = '4' THEN 1 ELSE NULL END) as bangkok, "
		+ "COUNT(CASE WHEN p.province_id != 69 AND r.region_id = '4' THEN 1 ELSE NULL END) as center, "
		+ "COUNT(CASE WHEN r.region_id = '2' AND p.province_id = '19' THEN 1 ELSE NULL END) as sakonnakhon, "
		+ "COUNT(CASE WHEN r.region_id = '2' AND p.province_id != '19' THEN 1 ELSE NULL END) as northeast, "
		+ "COUNT(CASE WHEN r.region_id = '1' THEN 1 ELSE NULL END) as north, "
		+ "COUNT(CASE WHEN r.region_id = '5' THEN 1 ELSE NULL END) as east, "
		+ "COUNT(CASE WHEN r.region_id = '3' THEN 1 ELSE NULL END) as west, "
		+ "COUNT(CASE WHEN r.region_id = '6' THEN 1 ELSE NULL END) as south " + "FROM members_has_courses mhc "
		+ "LEFT JOIN members m ON mhc.member_id = m.member_id " + "LEFT JOIN courses c ON mhc.course_id = c.course_id "
		+ "LEFT JOIN gender g ON m.member_gender_id = g.gender_id "
		+ "LEFT JOIN transportations t ON mhc.tran_id = t.tran_id "
		+ "LEFT JOIN province p ON m.member_province_id = p.province_id "
		+ "LEFT JOIN region r ON p.region_id = r.region_id " + "WHERE 1=1 AND c.course_id = ? "
		+ "GROUP BY c.course_id")

@Entity
@Getter
@Setter
@ToString
public class ReportGenEntity implements Serializable {
	private static final long serialVersionUID = 5218040759577439311L;
	@Id
	private Long courseId;
	private String courseName;
	private Long genderM;
	private Long genderF;
	private Long genderOther;
	private Long transSelf;
	private Long transTemple;
	private Long newStudent;
	private Long bangkok;
	private Long center;
	private Long sakonnakhon;
	private Long northeast;
	private Long north;
	private Long east;
	private Long west;
	private Long south;
	private Long passCourse;
	private Long studyCourse;

	public ReportGenEntity() {
		super();
	}

	public ReportGenEntity(Long courseId, String courseName) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
	}

	public ReportGenEntity(Long genderM, Long genderF, Long genderOther, Long transSelf, Long transTemple, Long center,
			Long northeast, Long north, Long east, Long west, Long south) {
		super();
		this.genderM = genderM;
		this.genderF = genderF;
		this.genderOther = genderOther;
		this.transSelf = transSelf;
		this.transTemple = transTemple;
		this.center = center;
		this.northeast = northeast;
		this.north = north;
		this.east = east;
		this.west = west;
		this.south = south;
	}

	public ReportGenEntity(Long passCourse, Long studyCourse) {
		super();
		this.passCourse = passCourse;
		this.studyCourse = studyCourse;
	}

	public ReportGenEntity(Long courseId, String courseName, Long genderM, Long genderF, Long genderOther,
			Long transSelf, Long transTemple, Long newStudent, Long bangkok, Long center, Long sakonnakhon,
			Long northeast, Long north, Long east, Long west, Long south) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.genderM = genderM;
		this.genderF = genderF;
		this.genderOther = genderOther;
		this.transSelf = transSelf;
		this.transTemple = transTemple;
		this.newStudent = newStudent;
		this.bangkok = bangkok;
		this.center = center;
		this.sakonnakhon = sakonnakhon;
		this.northeast = northeast;
		this.north = north;
		this.east = east;
		this.west = west;
		this.south = south;
	}
}
