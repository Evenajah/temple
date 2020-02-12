package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.CourseTeacherDto;
import com.cdgs.temple.entity.CourseTeacherEntity;
import com.cdgs.temple.repository.CourseTeacherRepository;
import com.cdgs.temple.service.CourseTeacherService;

@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

	private final CourseTeacherRepository courseTeacherRepository;

	@Autowired
	public CourseTeacherServiceImpl(CourseTeacherRepository courseTeacherRepository) {
		this.courseTeacherRepository = courseTeacherRepository;
	}
	
	private List<CourseTeacherDto> mapEntityListToDtoList(List<CourseTeacherEntity> entities) {
		List<CourseTeacherDto> dto = new ArrayList<CourseTeacherDto>();
		for (CourseTeacherEntity entity : entities) {
			dto.add(mapEntityToDto(entity));
		}
		return dto;
	}

	private CourseTeacherDto mapEntityToDto(CourseTeacherEntity entity) {
		CourseTeacherDto dto = new CourseTeacherDto();
		dto.setCourseId(entity.getCourseId());
		dto.setMemberId(entity.getMemberId());
		return dto;
	}

	private CourseTeacherEntity mapDtoToEntity(CourseTeacherDto dto) {
		CourseTeacherEntity entity = new CourseTeacherEntity();
		entity.setCourseId(dto.getCourseId());
		entity.setMemberId(dto.getMemberId());
		return entity;
	}

	@Override
	public List<CourseTeacherDto> getCourseTeacher() {

		return mapEntityListToDtoList((List<CourseTeacherEntity>) courseTeacherRepository.findAll());
//		List<CourseTeacherEntity> entities = new ArrayList<>();
//		List<CourseScheduleDto> dto = new ArrayList<>();
//		
//		try {
////			entities = courseTeacherRepository.findAll();
//			dto = mapEntityListToDtoList(courseTeacherRepository.findAll());
//			return dto;
//		}catch (Exception e) {
//			
//		}
//		return null;
	}

	@Override
	public CourseTeacherDto getCourseTeacher(Long id) {
		return null;
	}

	@Override
	public CourseTeacherDto createCourseTeacher(CourseTeacherDto body) {
		CourseTeacherEntity entity = mapDtoToEntity(body);
		try {
			return mapEntityToDto(courseTeacherRepository.save(entity));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public CourseTeacherDto updateCourseTeacher(Long id, CourseTeacherDto body) {

		return null;
	}

	@Override
	public CourseTeacherDto deleteCourseTeacher() {
		// TODO Auto-generated method stub
		return null;
	}

}
