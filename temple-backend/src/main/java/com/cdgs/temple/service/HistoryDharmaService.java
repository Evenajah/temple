package com.cdgs.temple.service;

import java.util.List;

import com.cdgs.temple.dto.HistoryDharmaDto;

public interface HistoryDharmaService {

	List<HistoryDharmaDto> getAll() throws Exception;

	HistoryDharmaDto createHistoryDharma(HistoryDharmaDto body) throws Exception;

	List<HistoryDharmaDto> getHistoryDhamaByMemberId(Long memberId) throws Exception;

	Integer delHistoryDhamaById(Long delId) throws Exception;

	HistoryDharmaDto updateHistoryDhama(Long id, HistoryDharmaDto body) throws Exception;
}
