package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.LockerDto;

public interface LockerService {
	List<LockerDto> getAll();

	List<LockerDto> getAllByEnableIsTrueAndIsNotActive();

	LockerDto getLockerById(long lockerId);

	LockerDto create(LockerDto locker);

	LockerDto update(long lockerId, LockerDto locker);

	LockerDto delete(long lockerId);

}
