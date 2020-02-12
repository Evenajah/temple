package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.GraduatedCourseDto;

public interface GraduatedCourseService {

	public List<GraduatedCourseDto> getAll(String query, Long monkId, Long limit, Long offset);

	Integer countCourses(Long monkId);

}
