package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.ProvinceDto;
import com.cdgs.temple.service.ProvinceService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/provinces")

public class ProvinceController {

	private static final Logger log = LoggerFactory.getLogger(ProvinceController.class);

	@Autowired
	ProvinceService provinceService;

	@GetMapping(path = "")
	public ResponseEntity<ResponseDto<ProvinceDto>> getProvinces() {
		List<ProvinceDto> dto = new ArrayList<ProvinceDto>();
		ResponseDto<ProvinceDto> res = new ResponseDto<ProvinceDto>();
		try {
			dto = provinceService.getProvince();
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<ProvinceDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			log.error(e.getMessage());
			return new ResponseEntity<ResponseDto<ProvinceDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

}
