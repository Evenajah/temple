package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.ApprovalCoursesEntity;

@Repository
public interface ApprovalCoursesRepository extends CrudRepository<ApprovalCoursesEntity, Long> {
	// Query ข้อมูลคอร์สพิเศษที่อนุมัติ
	@Query(value = "SELECT c.course_id,c.course_name,c.course_condition_min,c.course_st_date,c.course_end_date,c.course_detail "
			+ ", COUNT(CASE WHEN sa.spa_status = '2' THEN 1 ELSE NULL END) as total_member "
			+ "FROM courses c "
			+ "INNER JOIN courses_teacher  ct on  ct.course_id = c.course_id "
			+ "INNER JOIN special_approve sa ON c.course_id=sa.course_id "
			+ "WHERE 1=1 AND ct.member_id = :monkId "
			+ "AND (CASE WHEN :query <> '' "
			+ "THEN (c.course_name LIKE CONCAT('%',:query,'%') "
			+ "OR c.course_detail LIKE CONCAT('%',:query,'%') "
			+ "OR c.course_condition_min LIKE CONCAT('%',:query,'%')) ELSE 1 END) "
			+ "GROUP BY c.course_id "
			+ "ORDER BY  total_member DESC, c.course_id "
			+ "limit :offset , :limit", nativeQuery = true)
	List<ApprovalCoursesEntity> getAll(@Param("query") String query, @Param("monkId") Long monkId,
			@Param("limit") int limit, @Param("offset") int offset);
}
