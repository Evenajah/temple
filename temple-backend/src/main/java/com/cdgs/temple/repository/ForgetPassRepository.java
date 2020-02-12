package com.cdgs.temple.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cdgs.temple.entity.ForgetPassEntity;

import java.util.List;

public interface ForgetPassRepository extends CrudRepository<ForgetPassEntity, Long> {
	List<ForgetPassEntity> findAll();

	@Query(value = "SELECT COUNT(mb.member_id) as count " + "FROM members mb "
			+ "WHERE 1=1 AND mb.member_username = :userName " + "AND mb.member_id_card = :idCard "
			+ "AND mb.member_tel = :phoneNumber", nativeQuery = true)
	Integer countUser(@Param("userName") String userName, @Param("idCard") String idCard,
			@Param("phoneNumber") String phoneNumber);

}
