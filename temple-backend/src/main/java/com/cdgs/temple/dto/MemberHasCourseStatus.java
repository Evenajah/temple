package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberHasCourseStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long mhcId;
	private char status;
}
