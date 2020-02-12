package com.cdgs.temple.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DashboardDto {
	private Long memberId;
	private Long genderMale;
	private Long genderFemale;
	/*** gender that not specified. **/
	private Long genderNotspec;
	private Long tranTemple;
	private Long transport;
	private Long central;
	/*** จังหวัด สกลนคร **/
	private Long northEast;
	private Long north;
	private Long south;
	private Long east;
	private Long western;
	/*** คอร์สที่ผ่าน **/
	private Long passCourse;
	/*** คอร์สที่กำลังศึกษา **/
	private Long studyCourse;
	private String province;
	private Long totalMemberHasCourse;
	
	public DashboardDto() {
		super();
	}

	public DashboardDto(Long memberId, Long genderMale, Long genderFemale, Long genderNotspec, Long tranTemple,
			Long transport, Long central, Long northEast, Long north, Long south, Long east, Long western,
			Long passCourse, Long studyCourse, String province, Long totalMemberHasCourse) {
		super();
		this.memberId = memberId;
		this.genderMale = genderMale;
		this.genderFemale = genderFemale;
		this.genderNotspec = genderNotspec;
		this.tranTemple = tranTemple;
		this.transport = transport;
		this.central = central;
		this.northEast = northEast;
		this.north = north;
		this.south = south;
		this.east = east;
		this.western = western;
		this.passCourse = passCourse;
		this.studyCourse = studyCourse;
		this.province = province;
		this.totalMemberHasCourse = totalMemberHasCourse;
	}
}
