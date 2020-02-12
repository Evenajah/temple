package com.cdgs.temple.service.impl;

import com.cdgs.temple.dto.BaggageDto;
import com.cdgs.temple.entity.BaggageEntity;
import com.cdgs.temple.entity.LockerEntity;
import com.cdgs.temple.repository.BaggageRepository;
import com.cdgs.temple.repository.LockerRepository;
import com.cdgs.temple.repository.MemberRepository;
import com.cdgs.temple.service.BaggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

@Service
public class BaggageServiceImpl implements BaggageService {

	private BaggageRepository baggageRepository;
	private MemberRepository memberRepository;
	private LockerRepository lockerRepository;

	@Autowired(required = false)
	public BaggageServiceImpl(BaggageRepository baggageRepository, LockerRepository lockerRepository,
			MemberRepository memberRepository) {
		this.baggageRepository = baggageRepository;
		this.lockerRepository = lockerRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public List<BaggageDto> getAll() {
		List<BaggageEntity> entities = baggageRepository.findAll();
		return mapListEntityToDto(entities);
	}

	@Override
	public List<BaggageDto> getByMemberId(Long memberId) {
		List<BaggageEntity> entities = baggageRepository.findAllByMemberId(memberId);
		return mapListEntityToDto(entities);
	}

	@Override
	public BaggageDto create(BaggageDto body) {

		LockerEntity lockerEntity = lockerRepository.findByLockerIdAndEnableIsTrue(body.getLockerId());
		if (lockerEntity.getIsActive() == '0') {
			body.setStatus('1');
			System.out.println(body);
			BaggageEntity entity = baggageRepository.save(mapDtoToEntity(body));
			lockerEntity.setIsActive('1');
			lockerRepository.save(lockerEntity);
			return mapEntityToDto(entity);
		} else {
			throw new RuntimeErrorException(null, "Can't not update. Locker is active");
		}

	}

	@Override
	public BaggageDto delete(Long baggageId) {
		BaggageEntity entity = baggageRepository.findAllByBaggageId(baggageId);
		if (!(entity == null)) {
			entity.setBaggageLastUpdate(LocalDateTime.now());
			entity.setStatus('3');
			return mapEntityToDto(baggageRepository.save(entity));
		}
		return null;
	}

	@Override
	public BaggageDto update(Long baggageId, BaggageDto body) {

		BaggageEntity entity = baggageRepository.findById(baggageId).get();
		entity.setBaggageId(baggageId);
		entity.setMemberId(body.getMemberId());
		entity.setStatus(body.getStatus());
		LockerEntity lockerEntity = lockerRepository.findByLockerIdAndEnableIsTrue(entity.getLockerId());
		lockerEntity.setIsActive('0');
		lockerEntity = lockerRepository.findByLockerIdAndEnableIsTrue(body.getLockerId());
		lockerEntity.setIsActive(body.getStatus());
		lockerRepository.save(lockerEntity);
		entity.setLockerId(body.getLockerId());
		entity.setMember(memberRepository.findById(body.getMemberId()).get());
		return mapEntityToDto(baggageRepository.save(entity));
	}

	private BaggageEntity mapDtoToEntity(BaggageDto dto) {
		BaggageEntity entity = new BaggageEntity();
		entity.setStatus(dto.getStatus());
		entity.setLockerId(dto.getLockerId());
		entity.setMemberId(dto.getMemberId());

		return entity;
	}

	private List<BaggageDto> mapListEntityToDto(List<BaggageEntity> entities) {
		List<BaggageDto> dtoList = new ArrayList<>();
		if (!entities.isEmpty()) {
			for (BaggageEntity entity : entities) {
				dtoList.add(mapEntityToDto(entity));
			}
		}
		return dtoList;
	}

	private BaggageDto mapEntityToDto(BaggageEntity entity) {
		BaggageDto dto = new BaggageDto();
		if (entity != null) {
			dto.setBaggageId(entity.getBaggageId());
			dto.setCreateDate(entity.getBaggageCreateDate());
			dto.setLastUpdate(entity.getBaggageLastUpdate());
			dto.setStatus(entity.getStatus());
			dto.setMemberId(entity.getMemberId());
			dto.setLockerId(entity.getLockerId());
			if (entity.getMember() == null) {
				dto.setMemberName(null);
			} else {
				String name = entity.getMember().getTitleName().getTitleDisplay() + ""
						+ entity.getMember().getMemberFname() + " " + entity.getMember().getMemberLname();
				dto.setMemberName(name);
			}
			if (entity.getLocker() == null) {
				dto.setLocker(null);
			} else {
				String locker = entity.getLocker().getLocation().getLocationName() + " "
						+ entity.getLocker().getLockerNumber();
				dto.setLocker(locker);
			}

		}
		return dto;
	}
}
