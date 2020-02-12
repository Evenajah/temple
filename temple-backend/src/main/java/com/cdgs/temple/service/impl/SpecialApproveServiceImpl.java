package com.cdgs.temple.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.SensationDto;
import com.cdgs.temple.dto.SpecialApproveDto;
import com.cdgs.temple.entity.MembersHasCourseEntity;
import com.cdgs.temple.entity.SpecialApproveEntity;
import com.cdgs.temple.entity.TempSpecialApproveEntity;
import com.cdgs.temple.entity.TransportationEntity;
import com.cdgs.temple.repository.MembersHasCourseRepository;
import com.cdgs.temple.repository.SpecialApproveRepository;
import com.cdgs.temple.repository.TempSpecialApproveRepository;
import com.cdgs.temple.service.SensationService;
import com.cdgs.temple.service.SpecialApproveService;

@Service
public class SpecialApproveServiceImpl implements SpecialApproveService {

	private static final Logger log = LoggerFactory.getLogger(SpecialApproveServiceImpl.class);

	private SpecialApproveRepository specialApproveRepository;
	private TempSpecialApproveRepository tempSpecialApproveRepository;
	private MembersHasCourseRepository membersHasCourseRepository;
	private SensationService sensationService;

	@Autowired
	public SpecialApproveServiceImpl(SpecialApproveRepository specialApproveRepository,
			TempSpecialApproveRepository tempSpecialApproveRepository,
			MembersHasCourseRepository membersHasCourseRepository, SensationService sensationService) {
		this.specialApproveRepository = specialApproveRepository;
		this.tempSpecialApproveRepository = tempSpecialApproveRepository;
		this.membersHasCourseRepository = membersHasCourseRepository;
		this.sensationService = sensationService;
	}

	@Override
	public List<SpecialApproveDto> getAll(Long memberId, Long courseId) {
		return mapTempEntityListToDto(tempSpecialApproveRepository.getAll(memberId, courseId));
	}

	@Override
	public SpecialApproveDto getById(Long id) {
		return mapEntityToDto(specialApproveRepository.findBySpecialApproveId(id));
	}

	@Override
	public SpecialApproveDto create(SpecialApproveDto body) {
		SensationDto tempSensation = new SensationDto();
		SensationDto sensation = new SensationDto();

		tempSensation.setId(body.getSenseId());
		tempSensation.setExpected(body.getExpected());
		tempSensation.setExperience(body.getExperience());
		sensation = sensationService.createSensation(tempSensation);
		body.setSenseId(sensation.getId());

		SpecialApproveEntity entity = mapDtoToEntity(body);

		try {
			return mapEntityToDto(specialApproveRepository.save(entity));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public SpecialApproveDto delete(Long courseId, Long memberId) {
		SpecialApproveEntity data = specialApproveRepository.findByCourseIdAndMemberIdAndStatus(courseId, memberId,
				"2");
		if (data != null) {
			data.setSpaStatus("3");
			data.setLastUpdate(LocalDateTime.now());
			return mapEntityToDto(specialApproveRepository.save(data));
		}
		return null;
	}

	@Override
	public SpecialApproveDto update(SpecialApproveDto body, Long memberId) {
		SpecialApproveEntity data = specialApproveRepository.fetchBySaIdAndMemberId(body.getSpecialApproveId(),
				memberId);
		if (data != null && !body.getStatus().equals("2") && !body.getStatus().equals("3")) {
			data.setSpaStatus(body.getStatus());
			data.setLastUpdate(LocalDateTime.now());
			return mapEntityToDto(specialApproveRepository.save(data));
		}
		return null;
	}

	@Override
	public boolean approve(SpecialApproveDto data) {
		TransportationEntity tranEntity = new TransportationEntity();
		MembersHasCourseEntity mhc = new MembersHasCourseEntity();
		mhc.setMemberId(data.getMemberId());
		mhc.setCourseId(data.getCourseId());
		mhc.setMhcStatus('2');
		mhc.setSenseId(data.getSenseId());
		tranEntity.setTransportationId(data.getTransportationId());
		mhc.setTranId(tranEntity);
		if (membersHasCourseRepository.save(mhc) != null) {
			return true;
		}
		return false;
	}

	@Override
	public SpecialApproveDto getApproveByCourseIdAndMemberId(Long courseId, Long memberId) {
		return mapEntityToDto(specialApproveRepository.getByCourseIdAndMemberId(courseId, memberId));
	}

	@Override
	public Boolean cancelApproveOutTime(Long specialApproveId) {
		try {
			specialApproveRepository.cancelApproveOutTime(specialApproveId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<SpecialApproveDto> getMemberOutTime(Long memberId, Long courseId) {
		return mapTempEntityListToDto(tempSpecialApproveRepository.getMemberOutTime(memberId, courseId));
	}

	@Override
	public SpecialApproveDto getByCourseIdAndMemberId(Long courseId, Long memberId) {
		return mapEntityToDto(specialApproveRepository.getByCourseIdAndMemberId(courseId, memberId));
	}

//	private List<SpecialApproveDto> mapEntityListToDto(List<SpecialApproveEntity> entities) {
//		List<SpecialApproveDto> dto = new ArrayList<>();
//		for (SpecialApproveEntity entity : entities) {
//			dto.add(mapEntityToDto(entity));
//		}
//		return dto;
//	}

	private List<SpecialApproveDto> mapTempEntityListToDto(List<TempSpecialApproveEntity> entities) {
		List<SpecialApproveDto> dto = new ArrayList<>();
		for (TempSpecialApproveEntity entity : entities) {
			dto.add(mapTempEntityToDto(entity));
		}
		return dto;
	}

	private SpecialApproveDto mapEntityToDto(SpecialApproveEntity entity) {
		SpecialApproveDto dto = new SpecialApproveDto();
		dto.setSpecialApproveId(entity.getSpecialApproveId());
		dto.setCourseId(entity.getCourseId());
		dto.setCreateDate(entity.getCreateDate());
		dto.setDetail(entity.getSpaDetail());
		dto.setLastUpdate(entity.getLastUpdate());
		dto.setMemberId(entity.getMemberId());
		dto.setStatus(entity.getSpaStatus());
		dto.setSenseId(entity.getSenseId());
		dto.setTransportationId(entity.getTranId());
		return dto;
	}

	private SpecialApproveDto mapTempEntityToDto(TempSpecialApproveEntity entity) {
		SpecialApproveDto dto = new SpecialApproveDto();
		dto.setSpecialApproveId(entity.getSpecialApproveId());
		dto.setMemberId(entity.getMemberId());
		dto.setDetail(entity.getSpaDetail());
		dto.setDisplayName(entity.getDisplayName());
		if (null != entity.getCourseId()) {
			dto.setCourseId(entity.getCourseId());
		}
		if (null != entity.getCourseName()) {
			dto.setCourseName(entity.getCourseName());
		}
		if (null != entity.getCourseDetail()) {
			dto.setCourseDetail(entity.getCourseDetail());
		}
		if (null != entity.getCourseStDate()) {
			dto.setStDate(new java.sql.Date(entity.getCourseStDate().getTime()));
		}
		if (null != entity.getCourseEndDate()) {
			dto.setEndDate(new java.sql.Date(entity.getCourseEndDate().getTime()));
		}
		dto.setTransportationName(entity.getTransportation());
		return dto;
	}

	private SpecialApproveEntity mapDtoToEntity(SpecialApproveDto dto) {
		SpecialApproveEntity entity = new SpecialApproveEntity();
		entity.setCourseId(dto.getCourseId());
		entity.setCreateDate(dto.getCreateDate());
		entity.setSpaDetail(dto.getDetail());
		entity.setLastUpdate(dto.getLastUpdate());
		entity.setMemberId(dto.getMemberId());
		entity.setSpaStatus(dto.getStatus());
		entity.setSenseId(dto.getSenseId());
		entity.setTranId(dto.getTransportationId());
		return entity;
	}

}
