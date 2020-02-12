package com.cdgs.temple.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cdgs.temple.entity.SpecialApproveEntity;

import java.util.List;

public interface SpecialApproveRepository extends CrudRepository<SpecialApproveEntity, Long> {

	List<SpecialApproveEntity> findAll();

	SpecialApproveEntity findBySpecialApproveId(Long saId);

	@Query(value = "SELECT sa.* FROM special_approve sa LEFT JOIN courses_teacher ct ON sa.course_id=ct.course_id "
			+ "WHERE sa.course_id = :courseId " + "AND sa.member_id = :memberId "
			+ "AND sa.spa_status = :status", nativeQuery = true)
	SpecialApproveEntity findByCourseIdAndMemberIdAndStatus(Long courseId, Long memberId, String status);

	@Query(value = "SELECT sa.* " + "		FROM special_approve sa "
			+ "		LEFT JOIN courses_teacher ct ON sa.course_id=ct.course_id "
			+ "		WHERE sa.special_approve_id=:saId AND ct.member_id=:memberId", nativeQuery = true)
	SpecialApproveEntity fetchBySaIdAndMemberId(Long saId, Long memberId);

	@Query(value = "SELECT spa.* " + "FROM special_approve spa "
			+ "LEFT JOIN courses c ON c.course_id = spa.course_id AND c.course_no <> '0' "
			+ "WHERE spa.course_id = :courseId AND spa.member_id = :memberId "
			+ "AND spa.spa_status NOT IN('0','3') ", nativeQuery = true)
	SpecialApproveEntity getByCourseIdAndMemberId(@Param("courseId") Long courseId, @Param("memberId") Long memberId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE special_approve as sa " + "SET sa.spa_status = '3'   " + "WHERE sa.course_id = :courseId   "
			+ "and sa.spa_status = '2'", nativeQuery = true)
	void cancelApprove(@Param("courseId") Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE special_approve as sa " + "SET sa.spa_status = '3'   "
			+ "WHERE sa.special_approve_id = :specialApproveId   " + "and sa.spa_status = '4'", nativeQuery = true)
	void cancelApproveOutTime(@Param("specialApproveId") Long id);
}
