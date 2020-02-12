package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.MemberEntity;

@Repository
public interface MemberRepository extends CrudRepository<MemberEntity, Long> {

	List<MemberEntity> findAll();

	MemberEntity findByMemberUsername(String username);

	List<MemberEntity> findAllByRole_RoleName(String roleName);

	@Query(value = "SELECT member_id, member_role_id, member_username, member_password, "
			+ "member_title_id, member_gender_id, member_fname, member_lname, member_address, member_tel, "
			+ "member_emergency_tel, member_email, member_register_date, member_last_update, member_job, member_other, member_other, member_blood, "
			+ "member_allergy_food, member_allergy_medicine, member_disease, member_emer_name, member_emer_relationship, member_enable, member_province_id, "
			+ "member_id_card, member_age, member_ordian_number, member_ordian_date, member_postal_code "
			+ "FROM temple.members;", nativeQuery = true)
	List<MemberEntity> getAllUsersWithOutImg();

	@Query(value = "SELECT * " + "FROM members mb " + "WHERE 1=1 AND mb.member_username = :userName "
			+ "AND mb.member_id_card = :idCard " + "AND mb.member_tel = :phoneNumber", nativeQuery = true)
	MemberEntity getMemberByUserNameIdCardPhoneNumber(@Param("userName") String userName,
			@Param("idCard") String idCard, @Param("phoneNumber") String phoneNumber);

	@Query(value = "SELECT m.* " + "FROM courses_teacher ct " + "LEFT JOIN courses c " + "ON c.course_id = ct.course_id "
			+ "LEFT JOIN members m " + "ON ct.member_id = m.member_id "
			+ "WHERE c.course_id = :courseId", nativeQuery = true)
	List<MemberEntity> getTeacherByCourseId(@Param("courseId") Long courseId);
}
