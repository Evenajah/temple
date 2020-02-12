package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdgs.temple.entity.MembersHasCourseEntity;

@Repository
@Transactional(readOnly = true)
public interface MembersHasCourseRepository extends CrudRepository<MembersHasCourseEntity, Long> {

	@Query(value = "select COUNT(*) pass_courses from members_has_courses mhc"
			+ " Where mhc.member_id = :id and mhc.mhc_status = '1'", nativeQuery = true)
	Long CountForPassCourse(@Param("id") Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE members_has_courses mhc" + " INNER JOIN courses_teacher ct on mhc.course_id = ct.course_id"
			+ " SET mhc.mhc_status = :status" + " WHERE ct.member_id = :monkId"
			+ " and mhc.members_has_course_id = :mchId", nativeQuery = true)
	void updateStatusMember(@Param("status") char status, @Param("monkId") Long monkId, @Param("mchId") Long mchId);

	List<MembersHasCourseEntity> findAll();

	@Query(value = "SELECT mhc.* FROM members_has_courses as mhc"
			+ "WHERE mhc.member_id = :memberId AND mhc.mhc_status = :status", nativeQuery = true)
	List<MembersHasCourseEntity> findAllByMemberIdAndStatus(Long memberId, char status);

	List<MembersHasCourseEntity> findAllByCourseId(Long courseId);
	
//	@Query(value = "SELECT t.tran_name, tt.tran_time_pickup, tt.tran_time_send FROM members_has_courses mh "
//			+ "LEFT JOIN transportations t ON mh.tran_id=t.tran_id "
//			+ "LEFT JOIN transportations_time tt ON t.tran_time_id=tt.tran_time_id "
//			+ "WHERE mh.member_id = :memberId "
//			+ "AND mh.course_id = :courseId", nativeQuery = true)
//	TransportationEntity findTranTempleAndCourseIdMemberId(@Param("courseId") Long courseId, @Param("memberId") Long memberId);
}
