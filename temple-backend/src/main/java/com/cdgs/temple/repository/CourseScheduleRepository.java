package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.CourseScheduleEntity;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseScheduleRepository extends CrudRepository<CourseScheduleEntity, Long> {

	List<CourseScheduleEntity> findAll();

	@Query(value = "SELECT courses.course_id, courses.course_name ,course_schedule_date ,  course_st_date  ,course_end_date "
			+ "FROM courses_schedule "
			+ "LEFT JOIN members_has_courses ON members_has_courses.course_id=courses_schedule.course_id "
			+ "LEFT JOIN courses ON courses_schedule.course_id = courses.course_id "
			+ "WHERE members_has_courses.member_id=:memberId AND members_has_courses.mhc_status='2' group by courses_schedule.course_id", nativeQuery = true)
	List<CourseScheduleEntity> findCourseScheduleByMemberId(@Param("memberId") Long memberId);

	@Query(value = "SELECT courses_schedule.course_id,courses_schedule.course_schedule_date "
			+ "FROM courses_schedule INNER JOIN courses_teacher ON courses_teacher.course_id=courses_schedule.course_id "
			+ "WHERE courses_teacher.member_id=:memberId group by courses_schedule.course_id", nativeQuery = true)
	List<CourseScheduleEntity> findCourseTeacherByMemberId(@Param("memberId") Long memberId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM courses_schedule WHERE course_id = :courseId", nativeQuery = true)
	void deleteCourseSchdule(@Param("courseId") Long courseId);

	List<CourseScheduleEntity> findAllByCourseId(Long courseId);

	@Query(value = "SELECT course_id FROM courses_schedule WHERE course_id = :courseId", nativeQuery = true)
	Integer findCourseId(@Param("courseId") Long courseId);

}
