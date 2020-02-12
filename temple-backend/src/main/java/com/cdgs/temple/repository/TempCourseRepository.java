package com.cdgs.temple.repository;

import com.cdgs.temple.entity.TempCourseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TempCourseRepository extends CrudRepository<TempCourseEntity, Long> {

	@Query(value = "SELECT t1.*,(CASE WHEN t1.mhc_status='1' THEN 'สำเร็จการศึกษา' WHEN t1.mhc_status='2' THEN 'กำลังศึกษา' "
			+ "ELSE (CASE WHEN t1.sa_status='2' THEN 'รอการอนุมัติ' ELSE 'ยังไม่ได้ลงทะเบียน' END) END) AS status_text"
			+ ",(CASE WHEN t1.mhc_status='1' OR t1.mhc_status='2' THEN 0 ELSE (CASE WHEN t1.sa_status='2' THEN 0 ELSE 1 END) END) AS can_register FROM ("
			+ " SELECT c.*,(" + " SELECT mhc2.mhc_status FROM members_has_courses mhc2 WHERE mhc2.register_date=("
			+ " SELECT MAX(register_date) FROM members_has_courses mhc WHERE mhc.member_id=:memberId AND mhc.course_id=c.course_id GROUP BY mhc.course_id) AND mhc2.member_id=:memberId ) AS mhc_status,("
			+ " SELECT sa2.spa_status FROM special_approve sa2 WHERE sa2.create_date=("
			+ " SELECT MAX(create_date) FROM special_approve sa WHERE sa.member_id=:memberId AND sa.course_id=c.course_id GROUP BY sa.course_id)) AS sa_status,("
			+ " SELECT MAX(cs.course_schedule_date) FROM courses_schedule cs WHERE cs.course_id=c.course_id GROUP BY cs.course_id) AS end_date,("
			+ " SELECT MIN(cs.course_schedule_date) FROM courses_schedule cs WHERE cs.course_id=c.course_id GROUP BY cs.course_id) AS st_date FROM courses c WHERE c.course_enable=TRUE) AS t1 "
			+ " WHERE t1.course_no = '0' " + "GROUP BY t1.course_id "
			+ "ORDER BY t1.mhc_status,t1.course_st_date ", nativeQuery = true)
	List<TempCourseEntity> findCoursesUserRegister(@Param("memberId") Long memberId);

	@Query(value = "SELECT t1.*,(CASE WHEN t1.mhc_status='1' THEN 'สำเร็จการศึกษา' WHEN t1.mhc_status='2' THEN 'กำลังศึกษา' "
			+ "ELSE (CASE WHEN t1.sa_status='2' THEN 'รอการอนุมัติ' ELSE 'ยังไม่ได้ลงทะเบียน' END) END) AS status_text"
			+ ",(CASE WHEN t1.mhc_status='1' OR t1.mhc_status='2' THEN 0 ELSE (CASE WHEN t1.sa_status='2' THEN 0 ELSE 1 END) END) AS can_register FROM ("
			+ " SELECT c.*,(" + " SELECT mhc2.mhc_status FROM members_has_courses mhc2 WHERE mhc2.register_date=("
			+ " SELECT MAX(register_date) FROM members_has_courses mhc WHERE mhc.member_id=:memberId AND mhc.course_id=c.course_id GROUP BY mhc.course_id) AND mhc2.member_id=:memberId ) AS mhc_status,("
			+ " SELECT sa2.spa_status FROM special_approve sa2 WHERE sa2.create_date=("
			+ " SELECT MAX(create_date) FROM special_approve sa WHERE sa.member_id=:memberId AND sa.course_id=c.course_id GROUP BY sa.course_id)) AS sa_status,("
			+ " SELECT MAX(cs.course_schedule_date) FROM courses_schedule cs WHERE cs.course_id=c.course_id GROUP BY cs.course_id) AS end_date,("
			+ " SELECT MIN(cs.course_schedule_date) FROM courses_schedule cs WHERE cs.course_id=c.course_id GROUP BY cs.course_id) AS st_date FROM courses c "
			+ "WHERE (CASE WHEN :enable <> '*' THEN c.course_enable=TRUE " + "ELSE 1 END)) AS t1 "
			+ "WHERE 1=1 AND t1.mhc_status = :status " + "GROUP BY t1.course_id "
			+ "ORDER BY t1.course_st_date", nativeQuery = true)
	List<TempCourseEntity> findCoursesUser(@Param("memberId") Long memberId, @Param("status") String status,
			@Param("enable") String enable);

	@Query(value = "SELECT t1.*," + "(CASE WHEN t1.mhc_status='1' THEN 'สำเร็จการศึกษา' "
			+ "WHEN t1.mhc_status='2' THEN 'กำลังศึกษา' " + "ELSE (CASE WHEN t1.sa_status='2' THEN 'รอการอนุมัติ' "
			+ "ELSE 'ยังไม่ได้ลงทะเบียน' END) END) AS status_text, "
			+ "(CASE WHEN t1.mhc_status='1' OR t1.mhc_status='2' THEN 0 "
			+ "ELSE (CASE WHEN t1.sa_status IN ('2','4') THEN 0 ELSE 1 END) END) AS can_register FROM ("
			+ " SELECT c.*,(" + " SELECT mhc2.mhc_status FROM members_has_courses mhc2 WHERE mhc2.register_date=("
			+ " SELECT MAX(register_date) FROM members_has_courses mhc WHERE mhc.member_id=:memberId AND mhc.course_id=c.course_id GROUP BY mhc.course_id)) AS mhc_status,("
			+ " SELECT sa2.spa_status FROM special_approve sa2 WHERE sa2.create_date=("
			+ " SELECT MAX(create_date) FROM special_approve sa WHERE sa.member_id=:memberId AND sa.course_id=c.course_id GROUP BY sa.course_id)) AS sa_status ,("
			+ " SELECT MAX(cs.course_schedule_date) FROM courses_schedule cs WHERE cs.course_id=c.course_id GROUP BY cs.course_id) AS end_date,("
			+ " SELECT MIN(cs.course_schedule_date) FROM courses_schedule cs WHERE cs.course_id=c.course_id GROUP BY cs.course_id) AS st_date FROM courses c WHERE c.course_id = :courseId) AS t1"
			+ " LEFT JOIN transportations tran ON t1.course_id = tran.course_id"
			+ " LEFT JOIN transportations_time tt ON tran.tran_time_id = tt.tran_time_id", nativeQuery = true)
	TempCourseEntity findCourseUserById(@Param("memberId") Long memberId, @Param("courseId") Long courseId);

}
