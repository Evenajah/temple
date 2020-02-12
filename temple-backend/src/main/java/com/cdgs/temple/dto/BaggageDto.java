package com.cdgs.temple.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaggageDto implements Serializable {

	private static final long serialVersionUID = -4621882744690940670L;

	private Long baggageId;
	private Long memberId;
	private Long lockerId;

	private char status;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdate;
	private String Locker;
	private String memberName;
}
