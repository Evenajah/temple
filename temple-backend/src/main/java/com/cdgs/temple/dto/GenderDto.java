package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GenderDto implements Serializable {
	private static final long serialVersionUID = -8763820756419086427L;
	private Long id;
	private String name;
}
