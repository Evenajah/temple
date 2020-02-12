package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.cdgs.temple.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.ForgetPassDto;
import com.cdgs.temple.dto.MemberDto;
import com.cdgs.temple.entity.MemberEntity;
import com.cdgs.temple.repository.MemberRepository;
import com.cdgs.temple.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	private final MemberRepository memberRepository;

	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public List<MemberDto> getMembers() {
		List<MemberEntity> memberEntities = new ArrayList<>();
		try {
			memberEntities = memberRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mapListEntityToDto(memberEntities);
	}

	@Override
	public List<MemberDto> getAllUsersWithOutImg() {
		List<MemberEntity> memberEntities = new ArrayList<>();
		try {
			memberEntities = memberRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mapListEntityToDto(memberEntities);
	}

	@Override
	public List<MemberDto> getTeacher() {
		List<MemberEntity> memberEntities = new ArrayList<>();
		try {
			memberEntities = memberRepository.findAllByRole_RoleName("monk");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mapListEntityToDto(memberEntities);
	}

	@Override
	public MemberDto getMember(Long id) {
		MemberEntity entity = memberRepository.findById(id).get();
		return mapEntityToDto(entity);
	}
	
	@Override
	public List<MemberDto> getMemberByCourseId(Long courseId) {
		List<MemberEntity> entities = memberRepository.getTeacherByCourseId(courseId);
		return mapListEntityToDto(entities);
	}

	@Override
	public MemberDto createMember(MemberDto member) {

		MemberEntity memberData = mapDtoToEntity(member);
		MemberEntity entity;
		if (memberData.getMemberRoleId() == null) {
			memberData.setMemberRoleId((long) 3);
		} else {
			memberData.setMemberRoleId((long) memberData.getMemberRoleId());
		}
		memberData.setMemberPassword(bCryptPasswordEncoder.encode(memberData.getMemberPassword()));
		entity = memberRepository.save(memberData);
		// return mapEntityToDtoFnSave(entity);
		return mapEntityToDto(entity);

	}

	@Override
	public MemberDto updateMember(Long id, MemberDto member) {
		MemberEntity memberConvert = mapDtoToEntity(member);
		MemberEntity entity = new MemberEntity();

		System.out.println(member.toString());

		Optional<MemberEntity> memberEntity = memberRepository.findById(id);
		if (!memberEntity.isPresent()) {
			return mapEntityToDto(memberEntity.get());
		}
		if (member.getImg() != null) {
			memberConvert.setMemberImg(Base64.getDecoder().decode(member.getImg()));
		} else {
			memberConvert.setMemberImg(memberEntity.get().getMemberImg());
		}
		memberConvert.setMemberId(id);
		memberConvert.setMemberUsername(memberEntity.get().getMemberUsername());
		memberConvert.setMemberPassword(memberEntity.get().getMemberPassword());

		memberConvert.setMemberRoleId(memberEntity.get().getRole().getRoleId());

		entity = memberRepository.save(memberConvert);
		return mapEntityToDto(entity);

	}

	@Override
	public MemberDto getMemberByUserNameIdCardPhoneNumber(ForgetPassDto body) {
		MemberEntity entity = memberRepository.getMemberByUserNameIdCardPhoneNumber(body.getUsername(),
				body.getIdCard(), body.getPhoneNumber());
		return mapEntityToDto(entity);
	}

	@Override
	public MemberDto createMemberByAdmin(MemberDto member) {
		MemberEntity memberData = mapDtoToEntity(member);
		MemberEntity entity;
		// memberData.setMemberRoleId((long) member.getRoleId());
		memberData.setMemberPassword(bCryptPasswordEncoder.encode(memberData.getMemberPassword()));
		entity = memberRepository.save(memberData);
		return mapEntityToDto(entity);
	}

	@Override
	public MemberDto updateMemberByAdmin(Long id, MemberDto member) {
		MemberEntity memberConvert = mapDtoToEntity(member);
		MemberEntity entity;

		Optional<MemberEntity> memberEntity = memberRepository.findById(id);
		if (!memberEntity.isPresent()) {
			return mapEntityToDto(memberEntity.get());
		}

		memberConvert.setMemberId(id);
		memberConvert.setMemberUsername(memberEntity.get().getMemberUsername());
		memberConvert.setMemberPassword(memberEntity.get().getMemberPassword());
		entity = memberRepository.save(memberConvert);
		return mapEntityToDto(entity);

	}

	@Override
	public MemberDto changePasswordMember(MemberDto body) {
		MemberEntity entity;
		MemberEntity memberData;
		Optional<MemberEntity> memberEntity = memberRepository.findById(body.getId());
		if (!memberEntity.isPresent()) {
			return mapEntityToDto(memberEntity.get());
		}
		memberData = memberEntity.get();
		memberData.setMemberPassword(bCryptPasswordEncoder.encode(body.getPassword()));
		entity = memberRepository.save(memberData);
		return mapEntityToDto(entity);
	}

	@Override
	public MemberDto getCurrentMember() {
		JwtUser member = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return mapEntityToDto(member.getMember());
	}

	private MemberEntity mapDtoToEntity(MemberDto member) {
		MemberEntity entity = new MemberEntity();
		entity.setMemberGenderId(member.getGenderId());
		entity.setMemberProvinceId(member.getProvinceId());
		entity.setMemberTitleId(member.getTitleId());
		entity.setMemberIdCard(member.getIdCard());
		entity.setMemberRoleId(member.getRoleId());
		entity.setMemberId(member.getId());
		entity.setMemberUsername(member.getUsername());
		entity.setMemberPassword(member.getPassword());
		entity.setMemberFname(member.getFname());
		entity.setMemberLname(member.getLname());
		entity.setMemberAddress(member.getAddress());
		entity.setMemberTel(member.getTel());
		entity.setMemberAge(member.getAge());
		entity.setMemberPostalCode(member.getPostalCode());
		entity.setOrdianNumber(member.getOrdianNumber());
		entity.setOrdianDate(member.getOrdianDate());
		entity.setMemberEmail(member.getEmail());
		if (member.getImg() != null) {
			entity.setMemberImg(Base64.getDecoder().decode(member.getImg()));
		}
		entity.setMemberRegisterDate(member.getRegisterDate());
		entity.setMemberLastUpdate(member.getLastUpdate());
		entity.setMemberJob(member.getJob());
		entity.setMemberOther(member.getOther());
		entity.setMemberBlood(member.getBlood());
		entity.setMemberAllergyFood(member.getAllergyFood());
		entity.setMemberAllergyMedicine(member.getAllergyMedicine());
		entity.setMemberDisease(member.getDisease());
		entity.setMemberEmerName(member.getEmergencyName());
		entity.setMemberEmerRelationship(member.getEmergencyRelationship());
		entity.setMemberEmergencyTel(member.getEmergencyTel());
		return entity;
	}

	private List<MemberDto> mapListEntityToDto(List<MemberEntity> entities) {
		List<MemberDto> dtoList = new ArrayList<>();
		if (!entities.isEmpty()) {
			for (MemberEntity entity : entities) {
				dtoList.add(mapEntityToDto(entity));
			}
		}
		return dtoList;
	}

	private MemberDto mapEntityToDto(MemberEntity entity) {
		MemberDto dto = new MemberDto();
		if (entity != null) {
			dto.setId(entity.getMemberId());
			dto.setUsername(entity.getMemberUsername());
			dto.setFname(entity.getMemberFname());
			dto.setLname(entity.getMemberLname());
			dto.setAddress(entity.getMemberAddress());
			dto.setTel(entity.getMemberTel());
			dto.setEmergencyTel(entity.getMemberEmergencyTel());
			dto.setEmail(entity.getMemberEmail());
			if (entity.getMemberImg() != null) {
				dto.setImg(Base64.getEncoder().encodeToString(entity.getMemberImg()));
			}
			dto.setRegisterDate(entity.getMemberRegisterDate());
			dto.setLastUpdate(entity.getMemberLastUpdate());
			dto.setJob(entity.getMemberJob());
			dto.setOther(entity.getMemberOther());
			dto.setBlood(entity.getMemberBlood());
			dto.setAllergyFood(entity.getMemberAllergyFood());
			dto.setAllergyMedicine(entity.getMemberAllergyMedicine());
			dto.setDisease(entity.getMemberDisease());
			dto.setEmergencyName(entity.getMemberEmerName());
			dto.setEmergencyRelationship(entity.getMemberEmerRelationship());
			dto.setIdCard(entity.getMemberIdCard());
			dto.setAge(entity.getMemberAge());
			dto.setPostalCode(entity.getMemberPostalCode());
			dto.setOrdianNumber(entity.getOrdianNumber());
			dto.setOrdianDate(entity.getOrdianDate());

			dto.setGenderId(entity.getMemberGenderId());
			if (entity.getGender() != null)
				dto.setGenderName(entity.getGender().getGenderName());

			dto.setTitleId(entity.getMemberTitleId());
			if (entity.getTitleName() != null) {
				dto.setTitleDisplay(entity.getTitleName().getTitleDisplay());
				dto.setTitleName(entity.getTitleName().getTitleName());
			}

			dto.setRoleId(entity.getMemberRoleId());
			if (entity.getRole() != null)
				dto.setRoleName(entity.getRole().getRoleName());

			dto.setProvinceId(entity.getMemberProvinceId());
			if (entity.getProvince() != null)
				dto.setProvinceName(entity.getProvince().getProvinceName());
		}
		return dto;
	}

}
