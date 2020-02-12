package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.TransportationDto;

public interface TransportationService {
	List<TransportationDto> getTransportationName();

	TransportationDto createTransportation(TransportationDto transporatation);

	TransportationDto updateTransportation(Long id, TransportationDto transportation);

	TransportationDto updateTransportationTemple(Long id, TransportationDto transportation);
	
	TransportationDto getTransportationById(Long id);

	Boolean deleteTransportation(Long id, TransportationDto transportation);

	List<TransportationDto> getTransportationTemple(Long courseId);
	
	List<TransportationDto> getTransportationTempleRegister(Long courseId);
	
	TransportationDto getTransportationTempleMember(Long courseId, Long memberId);

	Boolean deleteTransportationTemple(Long id);
	
	TransportationDto getTransportationByCourseId(Long courseId);

	List<TransportationDto> getTransportationTemple();

	List<TransportationDto> getTransportationTempleForCreateCourse();

}
