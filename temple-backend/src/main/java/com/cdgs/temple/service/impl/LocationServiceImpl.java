package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.LocationDto;
import com.cdgs.temple.entity.LocationEntity;
import com.cdgs.temple.repository.LocationRepository;
import com.cdgs.temple.repository.TransportationRepository;
import com.cdgs.temple.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	private static final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

	@Autowired(required = true)
	LocationRepository locationRepository;
	TransportationRepository transportationRepository;

	@Override
	public List<LocationDto> getLocations() {
		List<LocationEntity> locationEntities = new ArrayList<LocationEntity>();
		List<LocationDto> locationDto = new ArrayList<LocationDto>();
		try {
			locationEntities = locationRepository.findAllBylocationEnableIsTrue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (LocationEntity locationEntity : locationEntities) {
			locationDto.add(convEntitytoDto(locationEntity));
		}
		return locationDto;
	}

	@Override
	public LocationDto createLocation(LocationDto location) {
		LocationEntity entity = locationRepository.save(convDtotoEntity(location));
		return convEntitytoDto(entity);
	}

	@Override
	public LocationDto getLocation(long id) {
		LocationEntity entity = locationRepository.findById(id).get();
		return convEntitytoDto(entity);
	}

	@Override
	public LocationDto updateLocation(long id, LocationDto location) {
		LocationEntity locationData = convDtotoEntity(location);
		LocationEntity entity = new LocationEntity();
		Optional<LocationEntity> locationEntities = locationRepository.findById(id);
		if (!locationEntities.isPresent()) {
			return convEntitytoDto(locationEntities.get());
		}
		locationData.setLocationId(id);
		entity = locationRepository.save(locationData);
		return convEntitytoDto(entity);
	}

	private LocationEntity convDtotoEntity(LocationDto location) {
		LocationEntity entity = new LocationEntity();
		entity.setLocationId(location.getId());
		entity.setLocationName(location.getName());
		return entity;
	}

	private LocationDto convEntitytoDto(LocationEntity location) {
		LocationDto dto = new LocationDto();
		dto.setId(location.getLocationId());
		dto.setName(location.getLocationName());
		return dto;
	}

	@Override
	public Boolean deleteLocation(long id) {
		try {
			if (locationRepository.countUesLocationById(id) == 0) {
				LocationEntity entity = locationRepository.findById(id).get();
				entity.setLocationEnable(false);
				locationRepository.save(entity);
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

}
