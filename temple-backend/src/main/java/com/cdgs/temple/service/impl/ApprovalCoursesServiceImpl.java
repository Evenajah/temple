package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.ApprovalCoursesDto;
import com.cdgs.temple.entity.ApprovalCoursesEntity;
import com.cdgs.temple.repository.ApprovalCoursesRepository;
import com.cdgs.temple.service.ApprovalCoursesService;

@Service
public class ApprovalCoursesServiceImpl implements ApprovalCoursesService {

	@Autowired(required = false)
	ApprovalCoursesRepository approvalCoursesRepository;

	@Override
	public List<ApprovalCoursesDto> getApprovalCourses(String query, Long monkId, int limit, int offset) {
		List<ApprovalCoursesEntity> listAppCourse = approvalCoursesRepository.getAll(query, monkId, limit, offset);
		List<ApprovalCoursesDto> listAppDto = mapListEntityToListDto(listAppCourse);
		return listAppDto;

	}

	private List<ApprovalCoursesDto> mapListEntityToListDto(List<ApprovalCoursesEntity> listAppCourse) {
		List<ApprovalCoursesDto> dto = new ArrayList<>();
		for (ApprovalCoursesEntity appCourse : listAppCourse) {
			dto.add(mapEntityToDto(appCourse));
		}
		return dto;
	}

	private ApprovalCoursesDto mapEntityToDto(ApprovalCoursesEntity appCourse) {
		ApprovalCoursesDto dto = new ApprovalCoursesDto();
		dto.setId(appCourse.getCourseId());
		dto.setName(appCourse.getCourseName());
		dto.setConditionMin(appCourse.getCourseConditionMin());
		dto.setStDate(appCourse.getCourseStDate());
		dto.setEndDate(appCourse.getCourseEndDate());
		dto.setDetail(appCourse.getCourseDetail());
		dto.setNumberOfMembers(appCourse.getTotalMember());
		return dto;
	}
}
