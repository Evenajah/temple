package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdgs.temple.dto.AuthDto;
import com.cdgs.temple.dto.HistoryDharmaDto;
import com.cdgs.temple.dto.MemberDto;
import com.cdgs.temple.security.JwtTokenUtil;
import com.cdgs.temple.security.JwtUser;
import com.cdgs.temple.service.HistoryDharmaService;
import com.cdgs.temple.service.MemberService;
import com.cdgs.temple.util.ResponseDto;
import com.cdgs.temple.util.ResponseTokenDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {
	@Value("${jwt.header}")
	private String tokenHeader;
	private final MemberService memberService;
	private final HistoryDharmaService historyDharmaService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public AuthController(MemberService memberService, AuthenticationManager authenticationManager,
			HistoryDharmaService historyDharmaService, JwtTokenUtil jwtTokenUtil) {
		this.historyDharmaService = historyDharmaService;
		this.memberService = memberService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthDto user) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			final JwtUser userDetails = (JwtUser) authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token = jwtTokenUtil.generateToken(userDetails);
			return new ResponseEntity<>(new ResponseTokenDto(ResponseTokenDto.RESPONSE_RESULT.Success.getRes(),
					"Bearer", token, userDetails.getId(), userDetails.getUsername(),
					userDetails.getMember().getRole().getRoleName()), HttpStatus.OK);
		} catch (Exception e) {
			log.error("login", e);
			ResponseDto<AuthDto> res = new ResponseDto<>();
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage("Username หรือ Password ไม่ถูกต้อง ");
			res.setCode(401);
			return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping(path = "/register")
	public ResponseEntity<ResponseDto<MemberDto>> register(@Valid @RequestBody MemberDto body) {
		ResponseDto<MemberDto> res = new ResponseDto<>();
		List<MemberDto> members = new ArrayList<>();
		List<HistoryDharmaDto> historyDharma = new ArrayList<>();
		MemberDto member;
		try {
			member = memberService.createMember(body);
			historyDharma = body.getHistoryDharma();
			if (member != null) {
				members.add(member);
				historyDharma.forEach(historyDharmaData -> {
					historyDharmaData.setMemberId(member.getId());
					try {
						historyDharmaService.createHistoryDharma(historyDharmaData);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				});
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(members);
			res.setCode(201);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("register", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/loginWithToken")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<MemberDto>> getMemberByToken() {
		MemberDto member;
		List<MemberDto> dto = new ArrayList<>();
		ResponseDto<MemberDto> res = new ResponseDto<>();

		try {
			member = this.memberService.getCurrentMember();
			if (member != null) {
				dto.add(member);
			}
			res.setData(dto);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getMemberByToken", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage("Token is inValid");
			res.setCode(401);
			return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
		}
	}

}
