package com.cdgs.temple.repository;

import com.cdgs.temple.entity.GraduatedEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GraduatedRepository extends CrudRepository<GraduatedEntity, Long> {

	@Query(value = "SELECT ct.course_id, mhc.mhc_status, m.member_id, mhc.members_has_course_id, "
			+ "CONCAT(tn.title_display, m.member_fname, '  ', m.member_lname) display_name, "
			+ "c.course_name  FROM  members_has_courses mhc "
			+ "LEFT JOIN courses_teacher ct on mhc.course_id = ct.course_id "
			+ "INNER JOIN members m on mhc.member_id = m.member_id "
			+ "INNER JOIN courses c on ct.course_id = c.course_id "
			+ "INNER JOIN title_names tn on tn.title_id = m.member_title_id " + "WHERE ct.member_id = :memberId "
			+ "and ct.course_id = :courseId " + "and mhc.mhc_status = '2' "
			+ "ORDER by mhc.members_has_course_id ASC", nativeQuery = true)
	List<GraduatedEntity> getAll(@Param("memberId") Long memberId, @Param("courseId") Long courseId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE members_has_courses as mhc " + "SET mhc_status = '0'  " + "WHERE mhc.course_id = :courseId "
			+ "and mhc.mhc_status = '2'", nativeQuery = true)
	void cancelGraduted(@Param("courseId") Long id);
}
