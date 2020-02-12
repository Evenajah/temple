package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportGenDto implements Serializable {
	private static final long serialVersionUID = 8200068162138826880L;

	private Long coursesId;
	private String coursesName;
	private Long genderMale;
	private Long genderFemale;
	/*** gender that not specified. * */
	private Long genderNotspec;
	private Long tranTemple;
	private Long transport;
	private Long newStudent;
	private Long bangkok;
	private Long central;
	/*** จังหวัด สกลนคร * */
	private Long sakon;
	private Long northEast;
	private Long north;
	private Long south;
	private Long east;
	private Long western;
	/*** คอร์สที่ผ่าน **/
	private Long passCourse;
	/*** คอร์สที่กำลังศึกษา **/
	private Long studyCourse;
}
