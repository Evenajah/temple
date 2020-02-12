package com.cdgs.temple.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ApprovalCoursesEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 233160471002091091L;
	@Id
	private Long courseId;
	private String courseName;
	private int courseConditionMin;
	private Date courseStDate;
	private Date courseEndDate;
	private String courseDetail;
	private int totalMember;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseConditionMin() {
		return courseConditionMin;
	}

	public void setCourseConditionMin(int courseConditionMin) {
		this.courseConditionMin = courseConditionMin;
	}

	public Date getCourseStDate() {
		return courseStDate;
	}

	public void setCourseStDate(Date courseStDate) {
		this.courseStDate = courseStDate;
	}

	public Date getCourseEndDate() {
		return courseEndDate;
	}

	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public String getCourseDetail() {
		return courseDetail;
	}

	public void setCourseDetail(String courseDetail) {
		this.courseDetail = courseDetail;
	}

	public int getTotalMember() {
		return totalMember;
	}

	public void setTotalMember(int totalMember) {
		this.totalMember = totalMember;
	}

}
