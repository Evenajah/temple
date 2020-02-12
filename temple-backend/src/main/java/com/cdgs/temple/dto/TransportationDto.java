package com.cdgs.temple.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransportationDto implements Serializable {
	private static final long serialVersionUID = -5583529121863627541L;

	private Long id;
	private String name;
	private Long tranTimeId;
	private Date timePickUp;
	private Date timeSend;
	private Long courseId;
}
