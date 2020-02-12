package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.CourseTeacherDto;

public interface CourseTeacherService {
	List<CourseTeacherDto> getCourseTeacher();

	CourseTeacherDto getCourseTeacher(Long id);

	CourseTeacherDto updateCourseTeacher(Long id, CourseTeacherDto body);

	CourseTeacherDto deleteCourseTeacher();

	CourseTeacherDto createCourseTeacher(CourseTeacherDto body);
}
