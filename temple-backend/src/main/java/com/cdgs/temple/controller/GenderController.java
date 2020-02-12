package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.GenderDto;
import com.cdgs.temple.service.GenderService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/genders")
public class GenderController {

	@Autowired
	GenderService genderService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<ResponseDto<GenderDto>> getGenderById(@PathVariable("id") Long id) {
		ResponseDto<GenderDto> res = new ResponseDto<>();
		List<GenderDto> genders = new ArrayList<>();
		GenderDto gender;
		try {
			gender = genderService.getGenderById(id);
			if (!(gender == null)) {
				genders.add(gender);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(genders);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
}
