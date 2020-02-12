package com.cdgs.temple.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.TransportationDto;
import com.cdgs.temple.entity.TransportationTimeEntity;
import com.cdgs.temple.repository.TransportationTimeRepository;
import com.cdgs.temple.service.TransportationTimeService;

@Service
public class TransportationTimeServiceImpl implements TransportationTimeService {

	private static final Logger log = LoggerFactory.getLogger(TransportationTimeServiceImpl.class);

	@Autowired(required = true)
	TransportationTimeRepository transportationTimeRepository;

	/**
	 * createTransportation Description : this function insert data to DB. Params :
	 * transporatation : TransporatationDto create : 07/01/2563 By Thunya
	 * Phanmetharit
	 */
	public TransportationDto createTransportationTime(TransportationDto transporatationTime) {
		TransportationTimeEntity entity = new TransportationTimeEntity();
		try {
			entity = transportationTimeRepository.save(mapDtoToEntity(transporatationTime));
		} catch (Exception e) {
			log.error("CreateTransportationTime Error=>" + e.getMessage());
			e.printStackTrace();
		}
		return mapEntityToDto(entity);
	}

	/**
	 * mapListEntityToDto Description : this function is mapping list data of Entity
	 * to Dto. create : 07/01/2563 By Thunya Phanmetharit
	 */
//	private List<TransportationDto> mapListEntityToDto(List<TransportationTimeEntity> entities) {
//		List<TransportationDto> dtoList = new ArrayList<TransportationDto>();
//		if (entities != null) {
//			for (TransportationTimeEntity entity : entities) {
//				dtoList.add(mapEntityToDto(entity));
//			}
//		}
//		return dtoList;
//	}

	/**
	 * mapDtoToEntity Description : this function is mapping data of Dto to Entity.
	 * Params : dto : TransportationDto create : 07/01/2563 By Thunya Phanmetharit
	 */
	private TransportationTimeEntity mapDtoToEntity(TransportationDto dto) {
		TransportationTimeEntity entity = new TransportationTimeEntity();
		entity.setTransportationTimeId(dto.getTranTimeId());
		entity.setTransportationTempleTimePickup(dto.getTimePickUp());
		entity.setTransportationTempleTimeSend(dto.getTimeSend());
		return entity;
	}

	/**
	 * mapEntityToDto Description : this function is mapping data of Entity to Dto.
	 * Params : entity : TransportationEntity create : 07/01/2563 by Thunya
	 * Phanmetharit
	 */
	private TransportationDto mapEntityToDto(TransportationTimeEntity entity) {
		TransportationDto dto = new TransportationDto();
		if (entity != null) {
			dto.setTranTimeId(entity.getTransportationTimeId());
			dto.setTimePickUp(entity.getTransportationTempleTimePickup());
			dto.setTimeSend(entity.getTransportationTempleTimeSend());
		}
		return dto;
	}

}
