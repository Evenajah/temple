package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.LockerDto;
import com.cdgs.temple.dto.MemberDto;
import com.cdgs.temple.service.LockerService;
import com.cdgs.temple.service.MemberService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/lockers")
public class LockerController {

	private LockerService lockerService;
	private MemberService memberService;

	@Autowired
	public LockerController(LockerService lockerService, MemberService memberService) {
		this.lockerService = lockerService;
		this.memberService = memberService;
	}

	@GetMapping(path = "")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<LockerDto>> getAllByEnableIsTrueAndIsNotActive() {
		List<LockerDto> dto;
		ResponseDto<LockerDto> res = new ResponseDto<>();
		try {
			dto = lockerService.getAllByEnableIsTrueAndIsNotActive();
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/all")
	@PreAuthorize("hasRole('admin') or hasRole('monk')")
	public ResponseEntity<ResponseDto<LockerDto>> getAll() {
		List<LockerDto> dto;
		ResponseDto<LockerDto> res = new ResponseDto<>();
		try {

			dto = lockerService.getAll();

			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/{lockerId}}")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<LockerDto>> getById(@PathVariable("lockerId") Long lockerId

	) {
		ResponseDto<LockerDto> res = new ResponseDto<>();
		List<LockerDto> lockersDto = new ArrayList<>();
		LockerDto lockerDto;
		try {
			lockerDto = lockerService.getLockerById(lockerId);
			lockersDto.add(lockerDto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(lockersDto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "")
	@PreAuthorize("hasRole('admin') or hasRole('monk')")
	public ResponseEntity<ResponseDto<LockerDto>> create(@Valid @RequestBody LockerDto body) {
		ResponseDto<LockerDto> res = new ResponseDto<>();
		List<LockerDto> lockersDto = new ArrayList<>();
		LockerDto lockerDto;
		MemberDto member = memberService.getCurrentMember();
		try {
			body.setCreateBy(member.getId());
			body.setEnable(true);
			body.setIsActive('0');

			lockerDto = lockerService.create(body);
			lockersDto.add(lockerDto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(lockersDto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/{lockerId}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<LockerDto>> update(@PathVariable("lockerId") Long lockerId,
			@Valid @RequestBody LockerDto body) {
		ResponseDto<LockerDto> res = new ResponseDto<>();
		List<LockerDto> lockersDto = new ArrayList<>();
		LockerDto locker;
		try {
			locker = lockerService.update(lockerId, body);
			lockersDto.add(locker);
			res.setData(lockersDto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path = "/{lockerId}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<LockerDto>> delete(@PathVariable("lockerId") Long lockerId) {
		System.out.println(lockerId);
		ResponseDto<LockerDto> res = new ResponseDto<>();
		LockerDto locker;
		try {
			locker = lockerService.delete(lockerId);
			if (!(locker == null)) {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(200);
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(204);
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
}
