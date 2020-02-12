package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.SensationDto;

public interface SensationService {
	SensationDto createSensation(SensationDto body);

	List<SensationDto> getAllSensations();
}
