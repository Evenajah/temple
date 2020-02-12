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

import com.cdgs.temple.dto.TitleNameDto;
import com.cdgs.temple.service.TitleNameService;
import com.cdgs.temple.util.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/titlenames")
@Slf4j
public class TitleNameController {

	@Autowired
	TitleNameService titleNameService;

	@GetMapping(path = "")
	public ResponseEntity<ResponseDto<TitleNameDto>> getTitleNames() {
		List<TitleNameDto> dto = new ArrayList<TitleNameDto>();
		ResponseDto<TitleNameDto> res = new ResponseDto<TitleNameDto>();
		try {
			dto = titleNameService.getTitleNames();
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getTitleNames ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ResponseDto<TitleNameDto>> putTitleName(@PathVariable("id") Long id) {
		List<TitleNameDto> dto = new ArrayList<TitleNameDto>();
		ResponseDto<TitleNameDto> res = new ResponseDto<TitleNameDto>();
		TitleNameDto titleName = new TitleNameDto();
		try {
			titleName = titleNameService.getTitleName(id);
			if (titleName != null) {
				dto.add(titleName);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("putTitleName ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<TitleNameDto>> postTitleName(@Valid @RequestBody TitleNameDto body) {
		List<TitleNameDto> dto = new ArrayList<TitleNameDto>();
		ResponseDto<TitleNameDto> res = new ResponseDto<TitleNameDto>();
		TitleNameDto titleName = new TitleNameDto();
		List<TitleNameDto> oldTitles = new ArrayList<TitleNameDto>();
		oldTitles = titleNameService.getTitleNames();
		try {
			for (TitleNameDto titles : oldTitles) {
				if (titles.getName().equals(body.getName()) || titles.getDisplay().equals(body.getName())) {
					throw new Exception("titleName is duplicate");
				} else if (titles.getName().equals(body.getDisplay())
						|| titles.getDisplay().equals(body.getDisplay())) {
					throw new Exception("titleName is duplicate");
				}
			}
			titleName = titleNameService.createTitleName(body);
			if (titleName != null) {
				dto.add(titleName);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("postTitleName ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<TitleNameDto>> getTitleName(@PathVariable("id") Long id,
			@Valid @RequestBody TitleNameDto body) {
		log.error(body.toString());
		List<TitleNameDto> dto = new ArrayList<TitleNameDto>();
		ResponseDto<TitleNameDto> res = new ResponseDto<TitleNameDto>();
		TitleNameDto titleName = new TitleNameDto();
		body.toString();
		try {
			titleName = titleNameService.updateTitleName(id, body);
			if (titleName != null) {
				dto.add(titleName);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getTitleName ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<TitleNameDto>> deleteTitleName(@PathVariable("id") Long id) {
		ResponseDto<TitleNameDto> res = new ResponseDto<TitleNameDto>();
		boolean titleName;
		try {
			titleName = titleNameService.deleteTitleName(id);
			if (titleName) {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(200);
				log.info("deleteTitleName >> " + titleNameService.deleteTitleName(id));
			} else {
				res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
				throw new Exception("titleName is using");

			}
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error("deleteTitleName ", e);
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TitleNameDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

}
