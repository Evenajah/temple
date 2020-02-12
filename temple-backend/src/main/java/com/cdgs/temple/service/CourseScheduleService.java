package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.CourseScheduleDto;

public interface CourseScheduleService {
	List<CourseScheduleDto> getCourseScheduleByUser(Long memberId);

	CourseScheduleDto createCourseSchedule(CourseScheduleDto body);

	CourseScheduleDto updateCourseSchedule();

	CourseScheduleDto deleteCourseSchedule();

	List<CourseScheduleDto> getCourseScheduleByMonk(Long memberId);

	List<CourseScheduleDto> getCourseScheduleList(Long courseId);

	Integer getCourseId(Long courseId);
}
