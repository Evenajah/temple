package com.cdgs.temple.repository;

import com.cdgs.temple.entity.BaggageEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaggageRepository extends CrudRepository<BaggageEntity, Long> {

	List<BaggageEntity> findAll();

	List<BaggageEntity> findAllByMemberId(long memberId);

	BaggageEntity findAllByBaggageId(Long baggageId);

}
