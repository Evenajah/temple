package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.MapEntity;

@Repository
public interface MapRepository extends CrudRepository<MapEntity, Long> {
	List<MapEntity> findAll();
}