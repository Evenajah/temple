package com.cdgs.temple.repository;

import com.cdgs.temple.entity.InsertCourseScheduleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsertCourseScheduleRepository extends CrudRepository<InsertCourseScheduleEntity, Long> {

	List<InsertCourseScheduleEntity> findAll();

}
