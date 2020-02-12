package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.MembersHasCourseDto;
import com.cdgs.temple.entity.MembersHasCourseEntity;
import com.cdgs.temple.repository.MembersHasCourseRepository;
import com.cdgs.temple.service.MembersHasCourseService;

@Service
public class MembersHasCoursesServiceImpl implements MembersHasCourseService {
	private static final Logger log = LoggerFactory.getLogger(TransportationServiceImpl.class);

	private final MembersHasCourseRepository memberHasCourseRepository;

	@Autowired
	public MembersHasCoursesServiceImpl(MembersHasCourseRepository memberHasCourseRepository) {
		this.memberHasCourseRepository = memberHasCourseRepository;
	}

	@Override
	public List<MembersHasCourseDto> getMembersHasCourse() {
		return mapListEntityToDto(memberHasCourseRepository.findAll());
	}

	@Override
	public MembersHasCourseDto createEntityMembersHasCourse() {
		return null;
	}

	@Override
	public MembersHasCourseDto updateMembersHasCourse() {
		return null;
	}

	@Override
	public MembersHasCourseDto deleteMembersHasCourse() {
		return null;
	}

	@Override
	public Long countForPassCourses(Long memberId) {
		return memberHasCourseRepository.CountForPassCourse(memberId);
	}

	@Override
	public List<MembersHasCourseDto> getMembersByCourse(Long courseId) {
		try {
			return mapListEntityToDto(memberHasCourseRepository.findAllByCourseId(courseId));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	private List<MembersHasCourseDto> mapListEntityToDto(List<MembersHasCourseEntity> entities) {
		List<MembersHasCourseDto> listDto = new ArrayList<>();
		if (!entities.isEmpty()) {
			for (MembersHasCourseEntity entity : entities) {
				listDto.add(mapEntityToDto(entity));
			}
		}
		return listDto;

	}

	private MembersHasCourseDto mapEntityToDto(MembersHasCourseEntity entity) {
		MembersHasCourseDto dto = new MembersHasCourseDto();
		if (entity != null) {
			dto.setMembersHasCourseId(entity.getMembersHasCourseId());
			dto.setMemberId(entity.getMemberId());
			dto.setCourseId(entity.getCourseId());
			dto.setRegisterDate(entity.getRegisterDate());
			dto.setStatus(entity.getMhcStatus());
			dto.setSenseId(entity.getSenseId());
		}
		return dto;
	}
	
	/**
	 * gettransportationByCourseId Description : this function is SELECT transportation data By course ID and member ID
	 * Params : course Id create : 13/01/2563 By Natthakit Suk-on
	 */
//	@Override
//	public TransportationDto getTransportationByCourseId(Long courseId, Long memberId) {
//		TransportationEntity entity = new TransportationEntity();
//		try {
//			entity = memberHasCourseRepository.findTranTempleAndCourseIdMemberId(courseId, memberId);
//		} catch (Exception e) {
//			log.error("getTransportationByCourseId >> " + e.getMessage());
//		}
//		return mapEntityToDto(entity);
//	}
	
//	private TransportationDto mapEntityToDto(TransportationEntity entity) {
//		TransportationDto dto = new TransportationDto();
//		if (entity != null) {
//			dto.setId(entity.getTransportationId());
//			dto.setName(entity.getTransportationName());
//			if (entity.getTransportationTimeEntity() != null) {
//				dto.setTranTimeId(entity.getTransportationTimeEntity().getTransportationTimeId());
//				dto.setTimePickUp(entity.getTransportationTimeEntity().getTransportationTempleTimePickup());
//				dto.setTimeSend(entity.getTransportationTimeEntity().getTransportationTempleTimeSend());
//			}
////			if (entity.getCoursesEntity() != null) {
////				dto.setCourseId(entity.getCoursesEntity().getCourseId());
////			}
//		}
//		return dto;
//	}

	
}
