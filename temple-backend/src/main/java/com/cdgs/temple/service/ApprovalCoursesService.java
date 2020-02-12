package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.ApprovalCoursesDto;

public interface ApprovalCoursesService {

	public List<ApprovalCoursesDto> getApprovalCourses(String query, Long monkId, int limit, int offset);

}
