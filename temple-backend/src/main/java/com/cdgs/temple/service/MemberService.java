package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.ForgetPassDto;
import com.cdgs.temple.dto.MemberDto;

public interface MemberService {
	List<MemberDto> getMembers();

	List<MemberDto> getAllUsersWithOutImg();

	MemberDto getMember(Long id);

	List<MemberDto> getMemberByCourseId(Long courseId);

	MemberDto createMember(MemberDto body);

	MemberDto updateMember(Long id, MemberDto body);

	MemberDto getCurrentMember();

	List<MemberDto> getTeacher();

	MemberDto createMemberByAdmin(MemberDto body);

	MemberDto updateMemberByAdmin(Long id, MemberDto body);

	MemberDto getMemberByUserNameIdCardPhoneNumber(ForgetPassDto body);

	MemberDto changePasswordMember(MemberDto body);
}
