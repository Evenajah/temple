package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.DashboardDto;

public interface DashboardService {

	DashboardDto getReportDashboardMonkData();

	DashboardDto getReportDashboardUserData(Long memberId);
	
	List<DashboardDto> getProvinceDashboardDataByRegionId(Long regionId);
}
