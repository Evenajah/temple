package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.TitleNameDto;

public interface TitleNameService {

	List<TitleNameDto> getTitleNames();

	TitleNameDto getTitleName(Long id);

	TitleNameDto updateTitleName(Long id, TitleNameDto titleName);

	TitleNameDto createTitleName(TitleNameDto titleName);

	Boolean deleteTitleName(Long id);
}
