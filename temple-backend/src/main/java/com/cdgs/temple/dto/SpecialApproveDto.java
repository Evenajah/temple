package com.cdgs.temple.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpecialApproveDto implements Serializable {

	private static final long serialVersionUID = -6856109137261338018L;

	private long specialApproveId;
	private List<Long> spaId;
	private long memberId;
	private long courseId;
	private String courseDetail;
	private String detail;
	private String status;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdate;
	private String courseName;
	private String displayName;
	private long senseId;
	private String expected;
	private String experience;
	private long transportationId;
	private String transportationName;

	private Date stDate;
	private Date endDate;

	private List<LocalDate> date;
}
