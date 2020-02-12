package com.cdgs.temple.service;

import com.cdgs.temple.dto.BaggageDto;

import java.util.List;

public interface BaggageService {
	List<BaggageDto> getAll();

	List<BaggageDto> getByMemberId(Long memberId);

	BaggageDto create(BaggageDto entity);

	BaggageDto delete(Long baggageId);

	BaggageDto update(Long baggageId, BaggageDto data);
}
