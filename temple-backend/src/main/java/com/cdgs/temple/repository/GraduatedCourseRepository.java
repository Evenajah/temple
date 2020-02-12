package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cdgs.temple.entity.GraduatedCourseEntity;

@Repository
public interface GraduatedCourseRepository extends CrudRepository<GraduatedCourseEntity, Long> {

	@Query(value = "SELECT courses.course_id,courses.course_name,courses.course_detail, "
			+ " (SELECT COUNT(mhc.members_has_course_id) FROM members_has_courses mhc  "
			+ "  WHERE mhc.course_id = courses.course_id AND mhc.mhc_status = '2') as count_member "
			+ " FROM courses INNER JOIN courses_teacher on  courses_teacher.course_id = courses.course_id "
			+ " where course_enable = true AND courses_teacher.member_id = :monkId " + " AND "
			+ "(CASE WHEN :query <> '' THEN (course_name LIKE CONCAT('%',:query,'%')) ELSE 1 END ) "
			+ "limit :offset , :limit ", nativeQuery = true)
	List<GraduatedCourseEntity> getAll(@Param("query") String query, @Param("monkId") Long monkId,
			@Param("limit") Long limit, @Param("offset") Long offset);

	@Query(value = "SELECT count(courses.course_id)"
			+ " FROM courses INNER JOIN courses_teacher on  courses_teacher.course_id = courses.course_id "
			+ " where course_enable = true AND courses_teacher.member_id = :monkId ", nativeQuery = true)
	Integer getTotalRecord(@Param("monkId") Long monkId);
}
