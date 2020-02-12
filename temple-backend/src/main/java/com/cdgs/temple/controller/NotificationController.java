package com.cdgs.temple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.CourseDto;
import com.cdgs.temple.service.CourseService;
import com.cdgs.temple.util.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/notification")
@Slf4j
public class NotificationController {
	@Autowired
	private CourseService courseService;

	@GetMapping(path = "/previouspast")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<CourseDto>> getAllPreviouspast() {
		ResponseDto<CourseDto> res = new ResponseDto<>();
		List<CourseDto> courseDto = courseService.getLastedCourses();
		try {
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(courseDto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			log.error("getAllPreviouspast", e);
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
}