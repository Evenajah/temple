package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.CourseDto;
import com.cdgs.temple.dto.MembersHasCourseDto;

public interface CourseService {
	List<CourseDto> getCoursesMonk(Long memberId);

	List<CourseDto> getCourses();

	List<CourseDto> getHistory(Long memberId);

	List<CourseDto> TeacherGetCoursesApproval(Long memberId);

	List<CourseDto> getCoursesUser(Long memberId, String status);

	List<CourseDto> getCoursesUserRegister(Long memberId);

	CourseDto getCourse(Long id);

	CourseDto getCourseMonk(Long id);

	CourseDto getCourseUser(Long memberId, Long courseId);

	CourseDto createCourse(CourseDto body);

	CourseDto updateCourse(Long id, CourseDto body);

	MembersHasCourseDto assignCourse(MembersHasCourseDto body);

	Boolean deleteCourse(long id);

	Integer CountTeacherCoursesApproval(Long memberId);

	Integer countCourses();

	Integer countUserRegisterCourses(Long memberId);

	Integer countMonkCourses(Long memberId);

	Integer countUserCourses(Long memberId, String status);

	Integer getCoursesUserByCourseId(Long memberId, Long courseId);

	List<CourseDto> getCoursesSpecialApproveOutTime(Long memberId);

	List<CourseDto> getCoursesOutTime(Long memberId);

	List<CourseDto> getCoursesMemberHasCourseOutTime(Long memberId);

	List<CourseDto> getCoursesOutTimeByMemberId(Long memberId);

	List<CourseDto> getCoursesMemberToStudy(Long memberId);

	List<CourseDto> getCourseOutTimeFromSpecialApproveIdByMemberId(Long memberId);

	List<CourseDto> getCourseOutTimeFromSpecialApproveSuccess(Long memberId);

	CourseDto getCourseById(Long courseId);

	List<CourseDto> TeacherGetCoursesApprovalOutTime(Long memberId, int offset, int limit, String query);

	Integer CountTeacherCoursesApprovalOutTime(Long memberId);
	
	List <CourseDto> getLastedCourses();

	void updateCourseToEnable(Long id);

}
