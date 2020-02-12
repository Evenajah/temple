package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.CourseScheduleDto;
import com.cdgs.temple.entity.CourseScheduleEntity;
import com.cdgs.temple.entity.InsertCourseScheduleEntity;
import com.cdgs.temple.repository.CourseRepository;
import com.cdgs.temple.repository.CourseScheduleRepository;
import com.cdgs.temple.repository.InsertCourseScheduleRepository;
import com.cdgs.temple.service.CourseScheduleService;

@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {

	private static final Logger log = LoggerFactory.getLogger(CourseScheduleServiceImpl.class);

	private CourseScheduleRepository courseScheduleRepository;
	private InsertCourseScheduleRepository insertCourseScheduleRepository;
	private CourseRepository courseRepository;

	@Autowired
	public CourseScheduleServiceImpl(CourseScheduleRepository courseScheduleRepository,
			InsertCourseScheduleRepository insertCourseScheduleRepository, CourseRepository courseRepository) {
		this.courseScheduleRepository = courseScheduleRepository;
		this.insertCourseScheduleRepository = insertCourseScheduleRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public List<CourseScheduleDto> getCourseScheduleByUser(Long memberId) {
		List<CourseScheduleEntity> entities;
		List<CourseScheduleDto> dto;
		try {
			entities = courseScheduleRepository.findCourseScheduleByMemberId(memberId);
			dto = mapEntityListToDtoList(entities);
		} catch (Exception e) {
			log.error("getCourseScheduleByUser>> " + e.getMessage());
			dto = null;
		}
		return dto;

	}

	@Override
	public List<CourseScheduleDto> getCourseScheduleByMonk(Long memberId) {
		List<CourseScheduleEntity> entities;
		List<CourseScheduleDto> dto;
		try {
			entities = courseScheduleRepository.findCourseTeacherByMemberId(memberId);
			dto = mapEntityListToDtoList(entities);
		} catch (Exception e) {
			log.error("getCourseScheduleByMonk>> " + e.getMessage());
			dto = null;
		}
		return dto;
	}

	@Override
	public List<CourseScheduleDto> getCourseScheduleList(Long courseId) {
		List<CourseScheduleEntity> courseScheduleList = new ArrayList<>();
		List<CourseScheduleDto> dtoList;
		courseScheduleList = courseScheduleRepository.findAllByCourseId(courseId);
		dtoList = mapEntityListToDtoList(courseScheduleList);
		return dtoList;
	}

	@Override
	public CourseScheduleDto createCourseSchedule(CourseScheduleDto body) {
		InsertCourseScheduleEntity entity;

		try {
			entity = insertCourseScheduleRepository.save(mapDtoToInsertEntity(body));
			System.out.println(entity);
			return mapInsertEntityToDto(entity);
		} catch (Exception e) {
			log.error("createCourseSchedule>> " + e.getMessage());
			return null;
		}
	}

	@Override
	public CourseScheduleDto updateCourseSchedule() {
		return null;
	}

	@Override
	public CourseScheduleDto deleteCourseSchedule() {
		return null;
	}

	private List<CourseScheduleDto> mapEntityListToDtoList(List<CourseScheduleEntity> entities) {
		List<CourseScheduleDto> dto = new ArrayList<>();
		for (CourseScheduleEntity entity : entities) {
			dto.add(mapEntityToDto(entity));
		}
		return dto;
	}

	private CourseScheduleDto mapEntityToDto(CourseScheduleEntity entity) {
		CourseScheduleDto dto = new CourseScheduleDto();
		dto.setCourseId(entity.getCourseId());
		dto.setCourseScheduleDate(entity.getCourseScheduleDate());
		// dto.setCourse(entity.getCourse());
		if (entity.getCourseId() != null) {
			dto.setCourse(courseRepository.findById(entity.getCourseId()).get());
		}
		return dto;
	}

	private CourseScheduleDto mapInsertEntityToDto(InsertCourseScheduleEntity entity) {
		CourseScheduleDto dto = new CourseScheduleDto();
		dto.setCourseId(entity.getCourseId());
		dto.setCourseScheduleDate(entity.getCourseScheduleDate());
		return dto;
	}

//	private CourseScheduleEntity mapDtoToEntity(CourseScheduleDto dto) {
//		CourseScheduleEntity entity = new CourseScheduleEntity();
//		entity.setCourseId(dto.getCourseId());
//		entity.setCourseScheduleDate(dto.getCourseScheduleDate());
//
//		return entity;
//	}

	private InsertCourseScheduleEntity mapDtoToInsertEntity(CourseScheduleDto dto) {
		InsertCourseScheduleEntity entity = new InsertCourseScheduleEntity();
		entity.setCourseId(dto.getCourseId());
		entity.setCourseScheduleDate(dto.getCourseScheduleDate());

		return entity;
	}

	/* ตรวจสอบหา course_id */
	@Override
	public Integer getCourseId(Long courseId) {
		return courseScheduleRepository.findCourseId(courseId);
	}

}
