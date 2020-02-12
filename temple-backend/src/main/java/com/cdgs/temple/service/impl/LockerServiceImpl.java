package com.cdgs.temple.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cdgs.temple.dto.LockerDto;
import com.cdgs.temple.entity.LockerEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.repository.LocationRepository;
import com.cdgs.temple.repository.LockerRepository;
import com.cdgs.temple.service.LockerService;

@Service
public class LockerServiceImpl implements LockerService {

	private static final Logger log = LoggerFactory.getLogger(LockerServiceImpl.class);

	private LockerRepository lockerRepository;
	private LocationRepository locationRepository;

	@Autowired(required = false)
	public LockerServiceImpl(LockerRepository lockerRepository, LocationRepository locationRepository) {
		this.lockerRepository = lockerRepository;
		this.locationRepository = locationRepository;
	}

	@Override
	public List<LockerDto> getAll() {
		List<LockerEntity> Lockers = lockerRepository.findAllByEnableIsTrue();
		return mapListEntityToDto(Lockers);
	}

	@Override
	public List<LockerDto> getAllByEnableIsTrueAndIsNotActive() {
		List<LockerEntity> Lockers = lockerRepository.findAllByEnableIsTrueAndIsActive('0');
		return mapListEntityToDto(Lockers);
	}

	@Override
	public LockerDto create(LockerDto locker) {

		LockerEntity entity = lockerRepository.save(mapDtoToEntity(locker));

		return mapEntityToDto(entity);

	}

	@Override
	public LockerDto getLockerById(long lockerId) {
		try {
			LockerEntity entity = lockerRepository.findByLockerId(lockerId);
			return mapEntityToDto(entity);
		} catch (Exception $e) {
			log.error("getLockerById" + $e.getMessage());
			System.out.println($e.getMessage());
			return null;
		}
	}

	@Override
	public LockerDto update(long lockerId, LockerDto data) {
		LockerEntity entity = lockerRepository.findAllByLockerId(lockerId);

		if (entity != null) {
			entity.setLockerNumber(data.getNumber());
			entity.setLocationId(data.getLocationId());
			entity.setLockerLastUpdate(LocalDateTime.now());
			return mapEntityToDto(lockerRepository.save(entity));

		}
		System.out.println("false");
		return null;

	}

	@Override
	public LockerDto delete(long lockerId) {
		LockerEntity entity = lockerRepository.findAllByLockerId(lockerId);
		if (entity.getIsActive() == '0') {
			entity.setLockerLastUpdate(LocalDateTime.now());
			entity.setEnable(false);
			return mapEntityToDto(lockerRepository.save(entity));
		}
		return null;

	}

	private LockerEntity mapDtoToEntity(LockerDto dto) {
		LockerEntity entity = new LockerEntity();
		entity.setLockerId(dto.getLockerId());
		entity.setLocationId(dto.getLocationId());
		entity.setLockerNumber(dto.getNumber());
		entity.setLockerCreateBy(dto.getCreateBy());
		entity.setLockerCreateDate(dto.getCreateDate());
		entity.setLockerLastUpdate(dto.getLastUpdate());
		entity.setIsActive(dto.getIsActive());
		entity.setEnable(dto.isEnable());
		return entity;
	}

	private List<LockerDto> mapListEntityToDto(List<LockerEntity> entities) {
		List<LockerDto> dtoList = new ArrayList<>();
		if (!entities.isEmpty()) {
			for (LockerEntity entity : entities) {
				dtoList.add(mapEntityToDto(entity));
			}
		}
		return dtoList;
	}

	private LockerDto mapEntityToDto(LockerEntity entity) {
		LockerDto dto = new LockerDto();
		String locationName;
		if (entity != null) {
			dto.setLockerId(entity.getLockerId());
			dto.setLocationId(entity.getLocationId());
			dto.setNumber(entity.getLockerNumber());
			dto.setCreateBy(entity.getLockerCreateBy());
			dto.setCreateDate(entity.getLockerCreateDate());
			dto.setIsActive(entity.getIsActive());
			dto.setEnable(entity.isEnable());
			dto.setLastUpdate(entity.getLockerLastUpdate());
			if (entity.getLocationId() != null) {
				locationName = locationRepository.findById(entity.getLocationId()).get().getLocationName();
				dto.setLocationName(locationName);
			}
		}
		return dto;
	}
}
