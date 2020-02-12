package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.SpecialApproveDto;

public interface SpecialApproveService {
	List<SpecialApproveDto> getAll(Long memberId, Long courseId);

	SpecialApproveDto getById(Long id);

	SpecialApproveDto create(SpecialApproveDto body);

	SpecialApproveDto delete(Long courseId, Long memberId);

	SpecialApproveDto update(SpecialApproveDto body, Long id);

	boolean approve(SpecialApproveDto data);

	SpecialApproveDto getApproveByCourseIdAndMemberId(Long courseId, Long memberId);

	Boolean cancelApproveOutTime(Long specialApproveId);

	List<SpecialApproveDto> getMemberOutTime(Long memberId, Long courseId);

	SpecialApproveDto getByCourseIdAndMemberId(Long courseId, Long memberId);
}
