package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.MembersHasCourseDto;

public interface MembersHasCourseService {
	List<MembersHasCourseDto> getMembersHasCourse();

	MembersHasCourseDto createEntityMembersHasCourse();

	MembersHasCourseDto updateMembersHasCourse();

	MembersHasCourseDto deleteMembersHasCourse();

	Long countForPassCourses(Long memberId);

	List<MembersHasCourseDto> getMembersByCourse(Long courseId);

}
