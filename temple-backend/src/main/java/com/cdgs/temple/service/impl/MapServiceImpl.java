package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.MapDto;
import com.cdgs.temple.entity.MapEntity;
import com.cdgs.temple.repository.MapRepository;
import com.cdgs.temple.service.MapService;

@Service
public class MapServiceImpl implements MapService {

	@Autowired(required = true)
	MapRepository mapRepository;

	@Override
	public List<MapDto> getMap() {
		List<MapEntity> mapEntities = new ArrayList<MapEntity>();
		List<MapDto> mapDto = new ArrayList<MapDto>();
		mapEntities = mapRepository.findAll();
		System.out.println(mapEntities.toString());
		mapDto = mapEntitiesToDto(mapEntities);
		return mapDto;
	}

	private List<MapDto> mapEntitiesToDto(List<MapEntity> mapEntities) {
		List<MapDto> mapDto = new ArrayList<MapDto>();
		for (MapEntity mapEntity : mapEntities) {
			mapDto.add(mapEntityToDto(mapEntity));
		}
		return mapDto;
	}

	private MapDto mapEntityToDto(MapEntity mapEntity) {
		MapDto mapDto = new MapDto();
		mapDto.setId(mapEntity.getMemberId());
		mapDto.setUsername(mapEntity.getMemberLname() + "   " + mapEntity.getMemberFname());
		mapDto.setGenderName(mapEntity.getGenderName());
		return mapDto;
	}

}
