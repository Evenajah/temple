package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.LocationEntity;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {
	LocationEntity findByLocationId(Long locationId);
	
	List<LocationEntity> findAllBylocationEnableIsTrue();

	@Query(value = "SELECT (count(c.course_location_id)+count(lk.location_id))AS sum FROM temple.locations l\r\n"
			+ "left JOIN courses c ON l.location_id=c.course_location_id AND c.course_enable=1\r\n"
			+ "left JOIN lockers lk ON l.location_id=lk.location_id AND lk.locker_enable=1\r\n"
			+ "where l.location_id=:locationId\r\n", nativeQuery = true)
	Integer countUesLocationById(@Param("locationId") Long locationId);

}
