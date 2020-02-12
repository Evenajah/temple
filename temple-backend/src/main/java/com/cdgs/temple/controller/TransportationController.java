package com.cdgs.temple.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cdgs.temple.dto.TransportationDto;
import com.cdgs.temple.service.TransportationService;
import com.cdgs.temple.service.TransportationTimeService;
import com.cdgs.temple.util.ResponseDto;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/transportations")
public class TransportationController {

	private static final Logger log = LoggerFactory.getLogger(TransportationController.class);

	private TransportationService transportationService;
	private TransportationTimeService transportationTimeService;

	@Autowired
	public TransportationController(TransportationService transportationService,
			TransportationTimeService transportationTimeService) {
		this.transportationService = transportationService;
		this.transportationTimeService = transportationTimeService;
	}

	/**
	 * getTransportation Description : this controller get data of transportation.
	 * Params : - create : 23/09/2562 By Korawit Ratyiam
	 */

	@GetMapping(path = "")
	public ResponseEntity<ResponseDto<TransportationDto>> getTransportation() {
		List<TransportationDto> dto = new ArrayList<>();
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		try {
			dto = transportationService.getTransportationName();
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			log.error(e.getMessage());
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		}
	}
	
	@GetMapping(path = "/templeRegister/{id}")
	public ResponseEntity<ResponseDto<TransportationDto>> getTransportationTempleRegister(@PathVariable("id") Long courseId) {
		List<TransportationDto> templeDto = new ArrayList<>();
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		try {
			templeDto = transportationService.getTransportationTempleRegister(courseId);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(templeDto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * postTransporatation Description : this controller insert data of
	 * transportation. Params : body : TransportationDto create : 23/09/2562 By
	 * Korawit Ratyiam
	 */
	@PostMapping(path = "")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<TransportationDto>> postTransporatation(
			@Valid @RequestBody TransportationDto body) {
		List<TransportationDto> dto = new ArrayList<>();
		TransportationDto transportation = new TransportationDto();
		TransportationDto tranTime = new TransportationDto();
		TransportationDto transportData = new TransportationDto();
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		try {
			if (body.getTimePickUp() != null && body.getTimeSend() != null) {
				tranTime = transportationTimeService.createTransportationTime(body);
			}
			try {
				transportData = body;
				if (tranTime != null) {
					transportData.setTranTimeId(tranTime.getTranTimeId());
				}
				transportation = transportationService.createTransportation(transportData);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			dto.add(transportation);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		}
	}

	/**
	 * updateTransportation Description : this controller update data of
	 * transportation. Params : body : TransportationDto, id : Long create :
	 * 23/09/2562 By Korawit Ratyiam
	 */

	@PutMapping(path = "/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<TransportationDto>> updateTransportation(@PathVariable("id") Long id,
			@Valid @RequestBody TransportationDto body) {
		List<TransportationDto> dto = new ArrayList<>();
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		TransportationDto transportation = new TransportationDto();
		try {
			transportation = transportationService.updateTransportation(id, body);
			if (transportation != null) {
				dto.add(transportation);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(dto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * deleteTransportation Description : this controller update status of
	 * transportation from 1 to 0. Params : body : TransportationDto, id : Long
	 * create : 23/09/2562 By Korawit Ratyiam PS. : This controller can't not delete
	 * data because this data is foreign Key of Sensation
	 */
	@PutMapping(path = "/delete/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<TransportationDto>> deleteTransportation(@PathVariable("id") Long id,
			@Valid @RequestBody TransportationDto body) {
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		Boolean transportation;
		try {
			transportation = transportationService.deleteTransportation(id, body);
			if (transportation) {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(200);
			} else {
				res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
				throw new Exception("transportation is using");
			}
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/********************************************************************************************************************************
	 * -------------------------------------Manage Data Transportation of Temple
	 * ---------------------------------------------------
	 */

	/**
	 * getTransportationTemple() This function for get transportation of temple
	 * 
	 */
	@GetMapping(path = "/temple")
	public ResponseEntity<ResponseDto<TransportationDto>> getTransportationTemple() {
		List<TransportationDto> templeDto = new ArrayList<>();
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		try {
			templeDto = transportationService.getTransportationTemple();
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(templeDto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * getTransportationTemple() This function for get transportation of temple
	 * 
	 */
	@GetMapping(path = "/templeForCreateCourse")
	public ResponseEntity<ResponseDto<TransportationDto>> getTransportationTempleForCreateCourse() {
		List<TransportationDto> templeDto = new ArrayList<>();
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		try {
			templeDto = transportationService.getTransportationTempleForCreateCourse();
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(templeDto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/temple/{id}")
	public ResponseEntity<ResponseDto<TransportationDto>> getTransportationTemple(@PathVariable("id") Long courseId) {
		List<TransportationDto> templeDto = new ArrayList<>();
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		try {
			templeDto = transportationService.getTransportationTemple(courseId);
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(templeDto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * updateTransportationTemple() This function for update transportation of
	 * temple params: id, body: TransportationTempleDto
	 * 
	 */
	@PutMapping(path = "/temple/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<TransportationDto>> updateTransportationTemple(@PathVariable("id") Long id,
			@Valid @RequestBody TransportationDto body) {
		List<TransportationDto> templeDto = new ArrayList<>();
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		TransportationDto transportationTemple = new TransportationDto();
		try {
			transportationTemple = transportationService.updateTransportationTemple(id, body);
			if (transportationTemple != null) {
				templeDto.add(transportationTemple);
			}
			res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
			res.setData(templeDto);
			res.setCode(200);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

//	 
//	 /**
//	  * deleteTransportationTemple()
//	  * This function for delete transportation of temple
//	  * params: id,  body: TransportationTempleDto
//	  * 
//	   */
//	 
	@DeleteMapping(path = "temple/delete/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ResponseDto<TransportationDto>> deleteTransportationTemple(@PathVariable("id") Long id) {
		ResponseDto<TransportationDto> res = new ResponseDto<TransportationDto>();
		Boolean delTransportationTemple;
		try {
			delTransportationTemple = transportationService.deleteTransportationTemple(id);
			if (delTransportationTemple) {
				res.setResult(ResponseDto.RESPONSE_RESULT.Success.getRes());
				res.setCode(200);
			} else {
				res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
				throw new Exception("transportationTemple is using");
			}
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.OK);
		} catch (Exception e) {
			res.setResult(ResponseDto.RESPONSE_RESULT.Fail.getRes());
			res.setErrorMessage(e.getMessage());
			res.setCode(400);
			return new ResponseEntity<ResponseDto<TransportationDto>>(res, HttpStatus.BAD_REQUEST);
		}
	}

}
