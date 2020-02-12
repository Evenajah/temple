package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseCountDto implements Serializable {
	private static final long serialVersionUID = 4708969530621243531L;
	private Integer totalRecord;
}
