package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TitleNameDto implements Serializable {
	private static final long serialVersionUID = -5313985153088542242L;

	private Long id;
	private String name;
	private String display;
}
