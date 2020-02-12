package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cdgs.temple.entity.CourseEntity;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {
	CourseEntity findByCourseId(Long id);
	
	List<CourseEntity> findAll();

	// count ข้อมูลของ admin
	Integer countAllBycourseEnableIsTrue();

	// count ข้อมูลที่ยังไม่ได้ลงทะเบียนของ user
	@Query(value = "SELECT  COUNT(c.course_id) FROM courses c " + "WHERE NOT EXISTS "
			+ "(SELECT mhc.course_id from members_has_courses mhc WHERE 1=1 AND mhc.member_id = :memberId AND c.course_id = mhc.course_id ) "
			+ "AND  c.course_enable = '1' AND c.course_no = 0", nativeQuery = true)
	Integer countUserAllBycourseEnableIsTrue(@Param("memberId") Long memberId);

	// count ข้อมูลของ user (กำลังเรียน = 2, สำเร็จการศึกษาแล้ว = 1)
	@Query(value = "select count(c.course_id) from courses c "
			+ "INNER JOIN members_has_courses mhc  ON c.course_id = mhc.course_id " + "where 1=1 AND "
			+ "(CASE WHEN :enable = '*' THEN mhc.mhc_status = :status "
			+ "ELSE mhc.mhc_status = :status AND c.course_enable = :enable " + "END) AND "
			+ "mhc.member_id = :memberId ", nativeQuery = true)
	Integer countCourseByEnableIsTrueAndStatus(@Param("memberId") Long memberId, @Param("status") String status,
			@Param("enable") String enable);

	// count ข้อมูลของ monk ตาม id
	@Query(value = "SELECT COUNT(c.course_id) FROM courses c "
			+ "INNER JOIN courses_teacher ct ON c.course_id=ct.course_id " + "WHERE 1=1 "
			+ "AND ct.member_id = :memberId  " + "AND c.course_enable = '1'", nativeQuery = true)
	Integer countCoursesTeacherAll(@Param("memberId") Long memberId);

	@Query(value = "SELECT COUNT(mhc.course_id) FROM members_has_courses mhc " + "WHERE mhc.course_id = :courseId "
			+ "AND mhc.member_id = :memberId "
			+ "AND (mhc.mhc_status = '1' OR mhc.mhc_status = '2')", nativeQuery = true)
	Integer findCoursesUserByCourseId(@Param("memberId") Long memberId, @Param("courseId") Long courseId);

	@Query(value = "SELECT c.* FROM courses c " + "INNER JOIN courses_teacher ct " + "ON c.course_id=ct.course_id "
			+ "INNER JOIN special_approve sa " + "ON c.course_id=sa.course_id "
			+ "WHERE 1=1 AND ct.member_id = :memberId " + "AND sa.spa_status='2' " + "GROUP BY c.course_id "
			+ "ORDER BY c.course_id ", nativeQuery = true)
	List<CourseEntity> fetchCoursesTeacherApproval(@Param("memberId") Long memberId);

	@Query(value = "SELECT COUNT(t1.course_id) FROM (" + "SELECT c.course_id FROM courses c "
			+ "INNER JOIN courses_teacher ct ON c.course_id=ct.course_id "
			+ "INNER JOIN special_approve sa ON c.course_id=sa.course_id "
			+ "WHERE 1=1 AND ct.member_id=:memberId AND sa.spa_status='2' "
			+ "GROUP BY c.course_id) t1", nativeQuery = true)
	Integer countCoursesTeacherApprovalAll(@Param("memberId") Long memberId);

	@Query(value = "SELECT * FROM courses c  " + "INNER JOIN courses_teacher ct " + "ON c.course_id=ct.course_id "
			+ "WHERE 1=1 " + "AND ct.member_id = :memberId " + "AND c.course_enable = '1' " + "GROUP BY c.course_id "
			+ "ORDER BY c.course_st_date,c.course_id ", nativeQuery = true)
	List<CourseEntity> findCoursesMonk(@Param("memberId") Long memberId);

//	@Query(value = "SELECT c.*, l.location_name, t.tran_id FROM courses c "
//			+ "LEFT JOIN locations l ON c.course_location_id = l.location_id "
//			+ "LEFT JOIN transportations t ON c.course_id = t.course_id "
//			+ "LEFT JOIN transportations_time tt ON t.tran_time_id = tt.tran_time_id ", nativeQuery = true )
	@Query(nativeQuery = true, name = "findAllCourseEntity")
	List<CourseEntity> findAllCourseEntity();

// ของเก่า
//	@Query(value = "SELECT c.*" + "		FROM courses c" + "		WHERE  c.course_no = '0'"
//			+ "		ORDER BY c.course_id", nativeQuery = true)

	@Query(value = "SELECT c.* FROM courses c LEFT JOIN special_approve spa "
			+ "ON spa.course_id = c.course_id LEFT JOIN members_has_courses mhc " + "ON mhc.course_id = c.course_id "
			+ "WHERE c.course_id <> (c.course_no <> '0' AND c.course_create_by = '1' AND spa.spa_status = '4') "
			+ "AND c.course_id NOT IN (SELECT sa.course_id FROM special_approve sa WHERE sa.spa_status <> '3') "
			+ "AND c.course_id NOT IN (SELECT c.course_no FROM courses c "
			+ "WHERE c.course_id IN (SELECT spa.course_id FROM special_approve spa WHERE spa.spa_status IN ('4', '1') "
			+ "AND spa.member_id = :memberId)) "
			+ "AND c.course_id NOT IN (SELECT mhc.course_id FROM members_has_courses mhc WHERE mhc.member_id = :memberId) "
			+ "AND c.course_status = '1' AND c.course_enable = '1' ORDER BY c.course_id;", nativeQuery = true)
	List<CourseEntity> getAllCourseOutTime(@Param("memberId") Long memberId);

	@Query(value = "SELECT c.*" + "		FROM courses c"
			+ "		LEFT JOIN special_approve spa ON spa.course_id = c.course_id AND spa.member_id = :memberId AND c.course_no = '0'"
			+ "		WHERE  spa.spa_status = '1' OR spa.spa_status = '2' OR spa.spa_status = '4'"
			+ "		ORDER BY c.course_id", nativeQuery = true)
	List<CourseEntity> getCourseSpecialApprove(@Param("memberId") Long memberId);

	@Query(value = "SELECT c.*" + "		FROM courses c"
			+ "		LEFT JOIN members_has_courses mhc ON mhc.course_id = c.course_id AND mhc.member_id = :memberId AND c.course_no = '0'"
			+ "		WHERE  mhc.mhc_status = '2' OR mhc.mhc_status = '1'"
			+ "		ORDER BY c.course_id", nativeQuery = true)
	List<CourseEntity> getCourseMemberHasCourse(@Param("memberId") Long memberId);

	@Query(value = "SELECT c.*" + "		FROM courses c"
			+ "		LEFT JOIN special_approve spa ON spa.course_id = c.course_id"
			+ "		WHERE  c.course_no <> '0' AND c.course_create_by = :memberId AND spa.spa_status = '4'"
			+ "		ORDER BY c.course_id", nativeQuery = true)
	List<CourseEntity> getCourseOutTimeByMemberId(@Param("memberId") Long memberId);

	@Query(value = "SELECT c.*" + "		FROM courses c"
			+ "		LEFT JOIN members_has_courses mhc ON mhc.course_id = c.course_id AND mhc.member_id = :memberId AND c.course_no <> '0'"
			+ "		WHERE  mhc.mhc_status = '2' OR mhc.mhc_status = '1'"
			+ "		ORDER BY c.course_id", nativeQuery = true)
	List<CourseEntity> getCoursesMemberToStudy(@Param("memberId") Long memberId);

	@Query(value = "SELECT c.*" + "		FROM courses c"
			+ "		LEFT JOIN special_approve spa ON spa.course_id = c.course_id AND spa.member_id = :memberId"
			+ "		WHERE spa.spa_status = '4'", nativeQuery = true)
	List<CourseEntity> getCourseSpecialApproveByMemberId(@Param("memberId") Long memberId);

	@Query(value = "SELECT c.*" + "		FROM courses c"
			+ "		LEFT JOIN special_approve spa ON spa.course_id = c.course_id AND spa.member_id = :memberId AND c.course_no <> '0'"
			+ "		WHERE spa.spa_status = '1'", nativeQuery = true)
	List<CourseEntity> getCourseSpecialApproveSuccess(@Param("memberId") Long memberId);

	@Query(value = "SELECT c.*" + "		FROM courses c "
			+ "		LEFT JOIN courses_teacher ct ON ct.course_id = c.course_id "
			+ "		LEFT JOIN special_approve spa ON spa.course_id = c.course_id "
			+ "		WHERE 1=1 AND c.course_status='0' AND ct.member_id=:memberId AND spa.spa_status = '4' AND (CASE WHEN :query <> '' THEN (c.course_name LIKE CONCAT('%',:query,'%') OR c.course_detail LIKE CONCAT('%',:query,'%') OR c.course_condition_min LIKE CONCAT('%',:query,'%')) ELSE 1 END) GROUP BY c.course_id ORDER BY c.course_id LIMIT :offset, :limit", nativeQuery = true)
	List<CourseEntity> fetchCoursesTeacherApprovalOutTime(@Param("memberId") Long memberId, @Param("offset") int offset,
			@Param("limit") int limit, @Param("query") String query);

	@Query(value = "SELECT COUNT(*) total_record FROM ("
			+ "SELECT c.course_id FROM courses c INNER JOIN courses_teacher ct ON c.course_id=ct.course_id INNER JOIN special_approve sa ON c.course_id=sa.course_id WHERE 1=1 AND ct.member_id=:memberId AND sa.spa_status='4' AND c.course_status='0' GROUP BY c.course_id) t1", nativeQuery = true)
	Integer countCoursesTeacherApprovalAllOutTime(@Param("memberId") Long memberId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE courses as c   " + "set c.course_enable = '0'  "
			+ "WHERE c.course_id = :courseId", nativeQuery = true)
	void deleteCourse(@Param("courseId") Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE courses as c   " + "set c.course_enable = 1  "
			+ "WHERE c.course_id = :courseId", nativeQuery = true)
	void updateCourseToEnable(@Param("courseId") Long id);
	
	@Query(value = "SELECT * FROM courses" + 
			       " WHERE course_create_date < DATE_ADD(NOW() , INTERVAL +1 MONTH)" +
			       " AND course_no = '0'"+
			       " ORDER BY course_create_date DESC", nativeQuery = true)
	List<CourseEntity> getLastedCourses();

}
