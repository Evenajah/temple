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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.SensationDto;
import com.cdgs.temple.service.SensationService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/sensations")
public class SensationsController {

	private static final Logger log = LoggerFactory.getLogger(SensationsController.class);

	@Autowired
	SensationService sensationService;

	@GetMapping(path = "")
	@PreAuthorize("hasRole('admin') or hasRole('monk')")
	public ResponseEntity<ResponseDto<SensationDto>> getAllSensations() {
		List<SensationDto> dto = new ArrayList<SensationDto>();
		ResponseDto<SensationDto> res = new ResponseDto<SensationDto>();
		try {
			dto = sensationService.getAllSensations();
			if (dto.size() <= 0) {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(204);
				return new ResponseEntity<ResponseDto<SensationDto>>(res, HttpStatus.NO_CONTENT);
			} else {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setData(dto);
				res.setCode(200);
				return new ResponseEntity<ResponseDto<SensationDto>>(res, HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<SensationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

}
