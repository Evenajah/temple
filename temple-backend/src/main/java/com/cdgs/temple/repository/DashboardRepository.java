package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.DashboardEntity;

@Repository
public interface DashboardRepository extends CrudRepository<DashboardEntity, Long> {
	@Query(nativeQuery = true, name = "getReportDashboardMonkData")
	DashboardEntity getReportDashboardMonkData();

	@Query(nativeQuery = true, name = "getReportDashboardUserData")
	DashboardEntity getReportDashboardUserData(@Param("memberId") Long memberId);

	@Query(nativeQuery = true, name = "getProvinceByRegionIdData")
	List<DashboardEntity> getProvinceDataByRegionId(@Param("regionId") Long regionId);
}
