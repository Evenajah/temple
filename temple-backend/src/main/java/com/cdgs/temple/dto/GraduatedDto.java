package com.cdgs.temple.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GraduatedDto implements Serializable {
	private static final long serialVersionUID = -8570567971745607790L;
	private Long uId;
	private Long cId;
	private Long mhcId;
	private String cName;
	private String fullname;
	private char status;
	private List<MemberHasCourseStatus> mhcList;
}