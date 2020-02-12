package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.ProvinceDto;
import com.cdgs.temple.entity.ProvinceEntity;
import com.cdgs.temple.repository.ProvinceRepository;
import com.cdgs.temple.service.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService {

	private static final Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);

	@Autowired(required = true)
	ProvinceRepository provinceRepository;

	public List<ProvinceDto> getProvince() {
		List<ProvinceEntity> provinceEntities = new ArrayList<ProvinceEntity>();
		try {
			provinceEntities = provinceRepository.getAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return convListEntityToDto(provinceEntities);
	}

	private List<ProvinceDto> convListEntityToDto(List<ProvinceEntity> entities) {
		List<ProvinceDto> dtoList = new ArrayList<ProvinceDto>();
		if (entities != null) {
			for (ProvinceEntity entity : entities) {
				dtoList.add(convEntityToDto(entity));
			}
		}
		return dtoList;
	}

	private ProvinceDto convEntityToDto(ProvinceEntity entity) {
		ProvinceDto dto = new ProvinceDto();
		try {
			if (entity != null) {
				dto.setProvinceId(entity.getProvinceId());
				dto.setProvinceName(entity.getProvinceName());
				dto.setRegionId(entity.getRegionId());
				dto.setRegionName(entity.getRegion().getRegionName());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return dto;
	}

}
