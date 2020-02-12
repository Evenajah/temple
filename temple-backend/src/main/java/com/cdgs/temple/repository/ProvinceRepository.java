package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.ProvinceEntity;

@Repository
public interface ProvinceRepository extends CrudRepository<ProvinceEntity, Long> {

	List<ProvinceEntity> findAll();

	@Query(value = "SELECT province.province_id, province.province_desc, province.region_id, region.region_name "
			+ "FROM province JOIN region ON province.region_id = region.region_id", nativeQuery = true)
	List<ProvinceEntity> getAll();
	
}