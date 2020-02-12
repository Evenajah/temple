package com.cdgs.temple.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cdgs.temple.dto.HistoryDharmaDto;
import com.cdgs.temple.service.HistoryDharmaService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/historydharma")
public class HistoryDharmaController {
	private static final Logger log = LoggerFactory.getLogger(HistoryDharmaController.class);

	private final HistoryDharmaService historyDharmaService;

	@Autowired
	public HistoryDharmaController(HistoryDharmaService historyDharmaService) {
		this.historyDharmaService = historyDharmaService;
	}

	@GetMapping(path = "")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<HistoryDharmaDto>> getAllHistoryDharma() {
		List<HistoryDharmaDto> dto;
		ResponseDto<HistoryDharmaDto> res = new ResponseDto<>();
		try {
			dto = historyDharmaService.getAll();
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setErrorMessage(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/getByMemberId")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<HistoryDharmaDto>> getHistoryDhamaByMemberId(
			@Valid @RequestBody HistoryDharmaDto body) {
		List<HistoryDharmaDto> dto;
		ResponseDto<HistoryDharmaDto> res = new ResponseDto<>();
		try {
			dto = historyDharmaService.getHistoryDhamaByMemberId(body.getMemberId());
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setErrorMessage(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "")
	@PreAuthorize("hasRole('admin') or hasRole('monk') or hasRole('user')")
	public ResponseEntity<ResponseDto<HistoryDharmaDto>> delHistoryDhamaById(
			@Valid @RequestBody List<HistoryDharmaDto> body) {
		ResponseDto<HistoryDharmaDto> res = new ResponseDto<>();
		try {
			if (body != null) {
				body.forEach(data -> {
					Long delId = data.getId();
					if (delId != null) {
						try {
							historyDharmaService.delHistoryDhamaById(delId);
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					}
				});
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setErrorMessage(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setCode(400);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

}
