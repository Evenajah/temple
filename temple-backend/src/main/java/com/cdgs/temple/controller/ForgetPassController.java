package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.ForgetPassDto;
import com.cdgs.temple.dto.MemberDto;
import com.cdgs.temple.service.ForgetPassService;
import com.cdgs.temple.service.MemberService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/forgetpass")
public class ForgetPassController {

	private static final Logger log = LoggerFactory.getLogger(ForgetPassController.class);

	private ForgetPassService forgetPassService;
	private MemberService memberService;

	@Autowired
	public ForgetPassController(ForgetPassService forgetPassService, MemberService memberService) {
		this.forgetPassService = forgetPassService;
		this.memberService = memberService;
	}

	@PostMapping(path = "/")
	public ResponseEntity<ResponseDto<MemberDto>> forgetPassword(@Valid @RequestBody ForgetPassDto body) {
		ResponseDto<MemberDto> res = new ResponseDto<>();
		List<MemberDto> dto = new ArrayList<>();
		MemberDto member;
		MemberDto member2 = new MemberDto();
		try {
			Integer count = forgetPassService.countUser(body.getUsername(), body.getIdCard(), body.getPhoneNumber());
			if (count > 0) {
				member = memberService.getMemberByUserNameIdCardPhoneNumber(body);
				member2.setId(member.getId());
				dto.add(member2);
				res.setData(dto);
				res.setStringData(count.toString());
				res.setStringData(count.toString());
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(200);
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(204);
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/")
	public ResponseEntity<ResponseDto<MemberDto>> changePassword(@Valid @RequestBody MemberDto body) {
		ResponseDto<MemberDto> res = new ResponseDto<>();
		try {
			memberService.changePasswordMember(body);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			res.setErrorMessage(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

}
