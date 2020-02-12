package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.MapDto;
import com.cdgs.temple.service.MapService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1")

public class MapController {

	@Autowired
	MapService mapService;

	@GetMapping(path = "/test")
	public ResponseEntity<ResponseDto<MapDto>> getTest() {
		List<MapDto> dto = new ArrayList<MapDto>();
		ResponseDto<MapDto> res = new ResponseDto<MapDto>();
		try {
			dto = mapService.getMap();
			res.setResult("test status");
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<MapDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<MapDto>>(res, HttpStatus.OK);
		}
	}
}
