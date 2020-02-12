package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.ReportGenEntity;

@Repository
public interface ReportGenRepository extends CrudRepository<ReportGenEntity, Long> {

	@Query(nativeQuery = true, name = "getAllDataReport")
	List<ReportGenEntity> getAllDataReport();
	
	@Query(nativeQuery = true, name = "getDataReportByCourseId")
	List<ReportGenEntity> getDataReportByCourseId(Long CourseId);
	
	@Query(nativeQuery = true, name = "findCourseName")
	List<ReportGenEntity> findCourseName();
}
