package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.GraduatedCourseDto;
import com.cdgs.temple.entity.GraduatedCourseEntity;
import com.cdgs.temple.repository.GraduatedCourseRepository;
import com.cdgs.temple.service.GraduatedCourseService;

@Service
public class GraduatedCourseImpl implements GraduatedCourseService {

	@Autowired(required = false)
	GraduatedCourseRepository graduatedCourseRepository;

	@Override
	public List<GraduatedCourseDto> getAll(String query, Long monkId, Long limit, Long offset) {
		List<GraduatedCourseEntity> listCourse = graduatedCourseRepository.getAll(query, monkId, limit, offset);
		List<GraduatedCourseDto> listCourseDto = mapListEntityToListDto(listCourse);
		return listCourseDto;
	}

	@Override
	public Integer countCourses(Long monkId) {
		return graduatedCourseRepository.getTotalRecord(monkId);
	}

	private List<GraduatedCourseDto> mapListEntityToListDto(List<GraduatedCourseEntity> listCourse) {
		List<GraduatedCourseDto> dto = new ArrayList<>();
		for (GraduatedCourseEntity course : listCourse) {
			dto.add(mapEntityToDto(course));
		}
		return dto;
	}

	private GraduatedCourseDto mapEntityToDto(GraduatedCourseEntity course) {
		GraduatedCourseDto dto = new GraduatedCourseDto();
		dto.setId(course.getCourseId());
		dto.setName(course.getCourseName());
		dto.setDetail(course.getCourseDetail());
		dto.setNumberOfMembers(course.getCountMember());
		return dto;
	}

}
