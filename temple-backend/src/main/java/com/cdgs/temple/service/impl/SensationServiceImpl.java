package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.SensationDto;
import com.cdgs.temple.entity.SensationEntity;
import com.cdgs.temple.repository.SensationRepository;
import com.cdgs.temple.service.SensationService;

@Service
public class SensationServiceImpl implements SensationService {

	private final SensationRepository sensationRepository;

	private static final Logger log = LoggerFactory.getLogger(SensationServiceImpl.class);

	@Autowired
	public SensationServiceImpl(SensationRepository sensationRepository) {
		this.sensationRepository = sensationRepository;
	}

	public SensationDto createSensation(SensationDto body) {
		SensationEntity entity = new SensationEntity();
		try {
			if (body != null) {
				entity = sensationRepository.save(mapDtoToEntity(body));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mapEntityToDto(entity);
	}

	@Override
	public List<SensationDto> getAllSensations() {
		List<SensationEntity> entity = new ArrayList<SensationEntity>();
		try {
			entity = sensationRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mapListEntityToDto(entity);
	}

	private SensationDto mapEntityToDto(SensationEntity entity) {
		SensationDto dto = new SensationDto();
		try {
			if (entity != null) {
				dto.setId(entity.getSenseId());
				dto.setExpected(entity.getSenseExpected());
				dto.setExperience(entity.getSenseExprience());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return dto;
	}

	private SensationEntity mapDtoToEntity(SensationDto sense) {
		SensationEntity entity = new SensationEntity();
		System.out.println(sense.toString());
		try {
			if (sense != null) {
				entity.setSenseId(sense.getId());
				entity.setSenseExpected(sense.getExpected());
				entity.setSenseExprience(sense.getExperience());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return entity;

	}

	private List<SensationDto> mapListEntityToDto(List<SensationEntity> entities) {
		List<SensationDto> dtoList = new ArrayList<SensationDto>();
		if (entities != null) {
			for (SensationEntity entity : entities) {
				dtoList.add(mapEntityToDto(entity));
			}
		}
		return dtoList;
	}
}
