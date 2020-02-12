package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MapDto implements Serializable {
	private static final long serialVersionUID = -1624843638255147242L;

	private Long id;
	private String username;
	private String genderName;
}
