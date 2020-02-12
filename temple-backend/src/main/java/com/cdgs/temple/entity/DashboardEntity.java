package com.cdgs.temple.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@NamedNativeQuery(name = "getReportDashboardMonkData", resultSetMapping = "getReportDashboardMonkDataMapping", query = "SELECT "
		+ "COUNT(CASE WHEN g.gender_id = '1' THEN 1 ELSE NULL END) AS genderM, "
		+ "COUNT(CASE WHEN g.gender_id = '2' THEN 1 ELSE NULL END) AS genderF, "
		+ "COUNT(CASE WHEN g.gender_id = '3' THEN 1 ELSE NULL END) AS genderOther, "
		+ "COUNT(CASE WHEN t.tran_time_id is Null THEN 1 ELSE NULL END) AS transSelf, "
		+ "COUNT(CASE WHEN t.tran_time_id is NOT Null THEN 1 ELSE NULL END) AS transTemple, "
		+ "COUNT(CASE WHEN r.region_id = '4' THEN 1 ELSE NULL END) as center, "
		+ "COUNT(CASE WHEN r.region_id = '2' THEN 1 ELSE NULL END) as northeast, "
		+ "COUNT(CASE WHEN r.region_id = '1' THEN 1 ELSE NULL END) as north, "
		+ "COUNT(CASE WHEN r.region_id = '5' THEN 1 ELSE NULL END) as east, "
		+ "COUNT(CASE WHEN r.region_id = '3' THEN 1 ELSE NULL END) as west, "
		+ "COUNT(CASE WHEN r.region_id = '6' THEN 1 ELSE NULL END) as south " + "FROM members_has_courses mhc "
		+ "LEFT JOIN members m ON mhc.member_id = m.member_id " + "LEFT JOIN courses c ON mhc.course_id = c.course_id "
		+ "LEFT JOIN gender g ON m.member_gender_id = g.gender_id "
		+ "LEFT JOIN transportations t ON mhc.tran_id = t.tran_id "
		+ "LEFT JOIN province p ON m.member_province_id = p.province_id "
		+ "LEFT JOIN region r ON p.region_id = r.region_id ")

@SqlResultSetMapping(name = "getReportDashboardMonkDataMapping", classes = {
		@ConstructorResult(targetClass = DashboardEntity.class, columns = {
				@ColumnResult(name = "genderM", type = Long.class), @ColumnResult(name = "genderF", type = Long.class),
				@ColumnResult(name = "genderOther", type = Long.class),
				@ColumnResult(name = "transSelf", type = Long.class),
				@ColumnResult(name = "transTemple", type = Long.class),
				@ColumnResult(name = "center", type = Long.class), @ColumnResult(name = "northeast", type = Long.class),
				@ColumnResult(name = "north", type = Long.class), @ColumnResult(name = "east", type = Long.class),
				@ColumnResult(name = "west", type = Long.class), @ColumnResult(name = "south", type = Long.class) }) })

@NamedNativeQuery(name = "getReportDashboardUserData", resultSetMapping = "getReportDashboardUserDataMapping", query = "SELECT "
		+ "COUNT(CASE WHEN mhc.mhc_status = '1' THEN 1 ELSE NULL END) AS passCourse, "
		+ "COUNT(CASE WHEN mhc.mhc_status = '2' THEN 1 ELSE NULL END) AS studyCourse " + "FROM members_has_courses mhc "
		+ "WHERE mhc.member_id = :memberId")

@SqlResultSetMapping(name = "getReportDashboardUserDataMapping", classes = {
		@ConstructorResult(targetClass = DashboardEntity.class, columns = {
				@ColumnResult(name = "passCourse", type = Long.class),
				@ColumnResult(name = "studyCourse", type = Long.class) }) })

@NamedNativeQuery(name = "getProvinceByRegionIdData", resultSetMapping = "getProvinceByRegionIdDataMapping", query = "SELECT "
		+ "p.province_desc AS province, COUNT(m.member_id) AS totalMemberHasCourse "
		+ "FROM members_has_courses mhc " + "LEFT JOIN members m ON mhc.member_id = m.member_id "
		+ "LEFT JOIN province p ON m.member_province_id = p.province_id " + "WHERE p.region_id = :regionId "
		+ "GROUP BY p.province_id")

@SqlResultSetMapping(name = "getProvinceByRegionIdDataMapping", classes = {
		@ConstructorResult(targetClass = DashboardEntity.class, columns = {
				@ColumnResult(name = "province", type = String.class),
				@ColumnResult(name = "totalMemberHasCourse", type = Long.class) }) })

@Entity
@Getter
@Setter
@ToString
public class DashboardEntity implements Serializable {
	private static final long serialVersionUID = -6411299710331678689L;

	@Id
	private Long memberId;
	private Long genderM;
	private Long genderF;
	private Long genderOther;
	private Long transSelf;
	private Long transTemple;
	private Long center;
	private Long northeast;
	private Long north;
	private Long east;
	private Long west;
	private Long south;
	private Long passCourse;
	private Long studyCourse;
	private String province;
	private Long totalMemberHasCourse;

	public DashboardEntity() {
		super();
	}

	public DashboardEntity(Long memberId, Long genderM, Long genderF, Long genderOther, Long transSelf,
			Long transTemple, Long center, Long northeast, Long north, Long east, Long west, Long south,
			Long passCourse, Long studyCourse, String province, Long totalMemberHasCourse) {
		super();
		this.memberId = memberId;
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
		this.passCourse = passCourse;
		this.studyCourse = studyCourse;
		this.province = province;
		this.totalMemberHasCourse = totalMemberHasCourse;
	}

	public DashboardEntity(Long genderM, Long genderF, Long genderOther, Long transSelf, Long transTemple, Long center,
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

	public DashboardEntity(Long passCourse, Long studyCourse) {
		super();
		this.passCourse = passCourse;
		this.studyCourse = studyCourse;
	}

	public DashboardEntity(String province, Long totalMemberHasCourse) {
		super();
		this.province = province;
		this.totalMemberHasCourse = totalMemberHasCourse;
	}
}
