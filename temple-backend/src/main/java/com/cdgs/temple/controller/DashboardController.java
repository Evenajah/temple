package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.DashboardDto;
import com.cdgs.temple.dto.MemberDto;
import com.cdgs.temple.service.DashboardService;
import com.cdgs.temple.service.MemberService;
import com.cdgs.temple.util.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/dashboard")
@Slf4j
public class DashboardController {
	private DashboardService dashboardService;
	private MemberService memberService;

	@Autowired
	public DashboardController(DashboardService dashboardService, MemberService memberService) {
		super();
		this.dashboardService = dashboardService;
		this.memberService = memberService;
	}

	@GetMapping(path = "")
	@PreAuthorize("hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<DashboardDto>> getDashboardData() {
		ResponseDto<DashboardDto> res = new ResponseDto<>();
		try {
			List<DashboardDto> listDto = new ArrayList<>();
			MemberDto member = memberService.getCurrentMember();
			DashboardDto dashboardDto = null;
			if (member.getRoleId() == Long.parseLong("2")) {
				dashboardDto = dashboardService.getReportDashboardMonkData();
			} else if (member.getRoleId() == Long.parseLong("3")) {
				dashboardDto = dashboardService.getReportDashboardUserData(member.getId());
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			listDto.add(dashboardDto);
			res.setData(listDto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getDashboardData ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/{region}")
	@PreAuthorize("hasRole('monk')")
	public ResponseEntity<ResponseDto<DashboardDto>> getProvinceDataByRegion(
			@PathVariable(value = "region") Long regionId) {
		ResponseDto<DashboardDto> res = new ResponseDto<>();
		try {
			List<DashboardDto> listDto;
			listDto = dashboardService.getProvinceDashboardDataByRegionId(regionId);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(listDto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getProvinceDataByRegion ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
}
