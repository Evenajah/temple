package com.cdgs.temple.service.impl;

import com.cdgs.temple.dto.GraduatedDto;
import com.cdgs.temple.entity.GraduatedEntity;
import com.cdgs.temple.repository.GraduatedRepository;
import com.cdgs.temple.repository.MembersHasCourseRepository;
import com.cdgs.temple.service.GraduatedService;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GraduatedServiceImpl implements GraduatedService {

	private final GraduatedRepository graduatedRepository;
	private final MembersHasCourseRepository memberHasCourseRepository;

	@Autowired
	public GraduatedServiceImpl(GraduatedRepository graduatedRepository,
			MembersHasCourseRepository memberHasCourseRepository) {
		this.graduatedRepository = graduatedRepository;
		this.memberHasCourseRepository = memberHasCourseRepository;
	}

	@Override
	public List<GraduatedDto> getAll(Long courseId, Long monkId) {
		List<GraduatedEntity> entities = graduatedRepository.getAll(monkId, courseId);
		List<GraduatedDto> dto = mapEntityesListToDtoList(entities);

		return dto;
	}

	@Override
	public boolean update(GraduatedDto body, Long id) {
		try {
			memberHasCourseRepository.updateStatusMember(body.getStatus(), id, body.getMhcId());
			return true;
		} catch (Exception e) {
			log.error("update >>> " + e);
			return false;
		}
	}

	private List<GraduatedDto> mapEntityesListToDtoList(List<GraduatedEntity> entities) {
		List<GraduatedDto> dto = new ArrayList<>();
		for (GraduatedEntity entity : entities) {
			dto.add(mapEntityesToDto(entity));
		}
		return dto;
	}

	private GraduatedDto mapEntityesToDto(GraduatedEntity entity) {
		GraduatedDto dto = new GraduatedDto();
		dto.setCId(entity.getMemberId());
		dto.setMhcId(entity.getMembersHasCourseId());
		dto.setCId(entity.getCourseId());
		dto.setFullname(entity.getDisplayName());
		dto.setStatus(entity.getMhcStatus());
		return dto;
	}

}
