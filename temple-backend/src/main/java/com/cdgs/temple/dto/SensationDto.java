package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SensationDto implements Serializable {
	private static final long serialVersionUID = 4117276160740695867L;

	private Long id;
	private String expected;
	private String experience;
}
