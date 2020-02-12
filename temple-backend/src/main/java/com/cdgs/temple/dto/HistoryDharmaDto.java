package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HistoryDharmaDto implements Serializable {
	private static final long serialVersionUID = 4907914194456485849L;
	private Long id;
	private String courseName;
	private String location;
	private Long memberId;
}
