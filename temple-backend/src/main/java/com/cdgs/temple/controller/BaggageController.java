package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.BaggageDto;
import com.cdgs.temple.dto.MemberDto;
import com.cdgs.temple.service.BaggageService;
import com.cdgs.temple.service.MemberService;
import com.cdgs.temple.util.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/baggage")
@Slf4j
public class BaggageController {

	private MemberService memberService;
	private BaggageService baggageService;

	@Autowired(required = false)
	public BaggageController(MemberService memberService, BaggageService baggageService) {
		this.memberService = memberService;
		this.baggageService = baggageService;
	}

	@GetMapping(path = "")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<BaggageDto>> getAll() {
		List<BaggageDto> dto;
		ResponseDto<BaggageDto> res = new ResponseDto<>();
		MemberDto member = memberService.getCurrentMember();

		try {
			if (member.getRoleName().equals("admin") || member.getRoleName().equals("monk")) {
				dto = baggageService.getAll();
			} else {
				dto = baggageService.getByMemberId(member.getId());
			}

			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getAll", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "")
	@PreAuthorize("hasRole('admin')  or hasRole('monk')")
	public ResponseEntity<ResponseDto<BaggageDto>> create(@Valid @RequestBody BaggageDto body) {
		ResponseDto<BaggageDto> res = new ResponseDto<>();
		List<BaggageDto> baggage = new ArrayList<>();
		BaggageDto dto;
		try {
			dto = baggageService.create(body);
			baggage.add(dto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(baggage);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("create", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/{id}")
	@PreAuthorize("hasRole('admin')  or hasRole('monk')")
	public ResponseEntity<ResponseDto<BaggageDto>> update(@Valid @RequestBody BaggageDto body,
			@PathVariable("id") Long id) {
		ResponseDto<BaggageDto> res = new ResponseDto<>();
		List<BaggageDto> baggage = new ArrayList<>();
		BaggageDto dto;
		try {
			dto = baggageService.update(id, body);

			if (body != null) {
				baggage.add(dto);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(baggage);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("update", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
}
