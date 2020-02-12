package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdgs.temple.entity.CourseTeacherEntity;
import com.cdgs.temple.entity.MemberEntity;

@Repository
public interface CourseTeacherRepository extends CrudRepository<CourseTeacherEntity, Long> {

//	@Query( value = "select courses_teacher.course_id ,course_teacher.memberId from courses_teacher"
//			+ " inner join members_has_courses on members_has_courses.course_id = courses_teacher.course_id WHERE members_has_courses.member_id = :id"
//			,nativeQuery = true)
//	List<CourseTeacherEntity> findCourseTeacherByMemberId(@Param("id") Long memberId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM courses_teacher WHERE course_id = :courseId", nativeQuery = true)
	void deleteCourseTeachers(@Param("courseId") Long courseId);

	List<CourseTeacherEntity> findAllByCourseId(Long courseId);
}
