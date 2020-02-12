package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.ReportGenDto;
import com.cdgs.temple.service.ReportGenService;
//import com.cdgs.temple.service.ReportService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/report")
public class ReportGenController {
	private static final Logger log = LoggerFactory.getLogger(ReportGenController.class);
	
	@Autowired
	private ReportGenService reportGenService;
	
	@GetMapping(path = "")
	@PreAuthorize("hasRole('admin') or hasRole('monk')")
	public ResponseEntity<ResponseDto<ReportGenDto>> getReport() {
		List<ReportGenDto> dto = new ArrayList<>();
		ResponseDto<ReportGenDto> res = new ResponseDto<ReportGenDto>();
		try {
			dto = reportGenService.getAllDataReport();
			
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			if (dto == null || dto.size() <= 0) {
				res.setCode(204);
				return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.NO_CONTENT);
			} else {
				res.setData(dto);
				res.setCode(200);
				return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	@PreAuthorize("hasRole('admin') or hasRole('monk')")
	public ResponseEntity<ResponseDto<ReportGenDto>> getReport(@PathVariable("id") Long courseId) {
		List<ReportGenDto> dto = new ArrayList<>();
		ResponseDto<ReportGenDto> res = new ResponseDto<ReportGenDto>();
		try {
			dto = reportGenService.getDataReportByCourseId(courseId);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			if (dto == null || dto.size() <= 0) {
				res.setCode(204);
				return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.NO_CONTENT);
			} else {
				res.setData(dto);
				res.setCode(200);
				return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Function: getCourseName() this function for get Course name to show in dropdown.
	 * parameter: -
	 * */
	@GetMapping(path = "/courseNamelist")
	@PreAuthorize("hasRole('admin') or hasRole('monk')")
	public ResponseEntity<ResponseDto<ReportGenDto>> getCourseName() {
		List<ReportGenDto> dto = new ArrayList<>();
		ResponseDto<ReportGenDto> res = new ResponseDto<ReportGenDto>();
		try {
			dto = reportGenService.findCourseName();
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			if (dto == null || dto.size() <= 0) {
				res.setCode(204);
				return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.NO_CONTENT);
			} else {
				res.setData(dto);
				res.setCode(200);
				return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<ReportGenDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}
}
