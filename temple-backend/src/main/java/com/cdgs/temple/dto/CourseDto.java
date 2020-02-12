package com.cdgs.temple.dto;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseDto implements Serializable {
	private static final long serialVersionUID = 234203082606309582L;

    private Long id;
    private Long no;
    private String name;
    private Date stDate;
    private Date endDate;
    private String detail;
    private int conditionMin;
    private Date createDate;
    private Date lastUpdate;
    private Long memberId;
    private String memberFname;
    private String memberLname;
    private Long locationId;
    private String locationName;
    private String mhcStatus;
    private String saStatus;
    private String status;
    private int canRegister;
    private List<Long> teacher;
    private List<MemberDto> teacherList;
    private List<CourseScheduleDto> dateList;
    private List<Date> date;
    private TransportationDto transportation;
    
    public CourseDto() {
    	super();
    }

    public CourseDto(Long id, Long no, String name, Date stDate, Date endDate, String detail, int conditionMin,
			Date createDate, Date lastUpdate, Long memberId, String memberFname, String memberLname, Long locationId,
			String locationName, String mhcStatus, String saStatus, String status, int canRegister, List<Long> teacher,
			List<MemberDto> teacherList, List<CourseScheduleDto> dateList, List<Date> date,
			TransportationDto transportation) {
		super();
		this.id = id;
		this.no = no;
		this.name = name;
		this.stDate = stDate;
		this.endDate = endDate;
		this.detail = detail;
		this.conditionMin = conditionMin;
		this.createDate = createDate;
		this.lastUpdate = lastUpdate;
		this.memberId = memberId;
		this.memberFname = memberFname;
		this.memberLname = memberLname;
		this.locationId = locationId;
		this.locationName = locationName;
		this.mhcStatus = mhcStatus;
		this.saStatus = saStatus;
		this.status = status;
		this.canRegister = canRegister;
		this.teacher = teacher;
		this.teacherList = teacherList;
		this.dateList = dateList;
		this.date = date;
		this.transportation = transportation;
	}
}
