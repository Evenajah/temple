package com.cdgs.temple.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto implements Serializable {
	private static final long serialVersionUID = 4601867391972524109L;
	private Long id;
	private String username;
	private String password;
	private String fname;
	private String lname;
	private String address;
	private String tel;
	private String emergencyTel;
	private String email;
	private String img;
	private LocalDateTime registerDate;
	private LocalDateTime lastUpdate;

	private Long roleId;
	private String roleName;

	private Long titleId;
	private String titleDisplay;
	private String titleName;

	private Long genderId;
	private String genderName;

	private String job;
	private String other;
	private String blood;

	/* ส่วนที่เพิ่มเติม */
	private String allergyFood;
	private String allergyMedicine;
	private String disease;
	private String emergencyName;
	private String emergencyRelationship;
	private boolean enable = true;

	private String idCard;
	private Long age;
	private Long ordianNumber;
	private LocalDateTime ordianDate;
	private String postalCode;

	private Long provinceId;
	private String provinceName;
//	private Long regionId;
//	private String regionName;
	private List<HistoryDharmaDto> historyDharma;
}
