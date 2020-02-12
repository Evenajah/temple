package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocationDto implements Serializable {
	private static final long serialVersionUID = 1917650945187274893L;
	private Long id;
	private String name;
}
