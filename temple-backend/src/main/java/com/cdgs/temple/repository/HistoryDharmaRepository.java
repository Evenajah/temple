package com.cdgs.temple.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.HistoryDharmaEntity;

@Repository
public interface HistoryDharmaRepository extends CrudRepository<HistoryDharmaEntity, Long> {

	List<HistoryDharmaEntity> findAll();

	@Query(value = "SELECT * " + "FROM history_dharma "
			+ "WHERE history_dharma_member_id = :memberId", nativeQuery = true)
	List<HistoryDharmaEntity> getHistoryDhamaByMemberId(@Param("memberId") Long memberId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM history_dharma " + "WHERE history_dharma_id = :delId", nativeQuery = true)
	Integer delHistoryDhamaById(@Param("delId") Long delId);

}
