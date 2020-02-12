package com.cdgs.temple.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.GenderDto;
import com.cdgs.temple.entity.GenderEntity;
import com.cdgs.temple.repository.GenderRepository;
import com.cdgs.temple.service.GenderService;

@Service
public class GenderServiceImpl implements GenderService {

	@Autowired(required = false)
	GenderRepository genderRepository;

	@Override
	public GenderDto getGenderById(long id) {
		GenderEntity entity = mapOptToEntity(genderRepository.findById(id));
		return convEntityToDto(entity);
	}

	private GenderEntity mapOptToEntity(Optional<GenderEntity> optEntity) {
		GenderEntity entity = new GenderEntity();
		if (optEntity.isPresent()) {
			entity.setGenderId(optEntity.get().getGenderId());
			entity.setGenderName(optEntity.get().getGenderName());
			return entity;
		} else {
			return null;
		}
	}

	private GenderDto convEntityToDto(GenderEntity gender) {
		GenderDto dto = new GenderDto();
		dto.setId(gender.getGenderId());
		dto.setName(gender.getGenderName());
		return dto;
	}
}
