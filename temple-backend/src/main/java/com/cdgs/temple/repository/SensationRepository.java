package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cdgs.temple.entity.SensationEntity;

public interface SensationRepository extends CrudRepository<SensationEntity, Long> {

	List<SensationEntity> findAll();

}
