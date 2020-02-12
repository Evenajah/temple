package com.cdgs.temple.service;

import java.util.List;
import com.cdgs.temple.dto.ReportGenDto;

public interface ReportGenService {

	List<ReportGenDto> getAllDataReport();
	
	List<ReportGenDto> getDataReportByCourseId(Long courseId);
	
	List<ReportGenDto> findCourseName();
}
