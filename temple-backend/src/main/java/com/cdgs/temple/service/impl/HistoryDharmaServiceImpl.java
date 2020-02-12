package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.HistoryDharmaDto;
import com.cdgs.temple.entity.HistoryDharmaEntity;
import com.cdgs.temple.repository.HistoryDharmaRepository;
import com.cdgs.temple.service.HistoryDharmaService;

@Service
public class HistoryDharmaServiceImpl implements HistoryDharmaService {
	private static final Logger log = LoggerFactory.getLogger(HistoryDharmaServiceImpl.class);

	private final HistoryDharmaRepository historyDharmaRepository;

	@Autowired
	public HistoryDharmaServiceImpl(HistoryDharmaRepository historyDharmaRepository) {
		this.historyDharmaRepository = historyDharmaRepository;
	}

	@Override
	public HistoryDharmaDto createHistoryDharma(HistoryDharmaDto body) throws Exception {
		HistoryDharmaEntity entity = new HistoryDharmaEntity();
		try {
			if (body != null) {
				entity = historyDharmaRepository.save(mapDtoToEntity(body));
			}
			return mapEntityToDto(entity);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<HistoryDharmaDto> getHistoryDhamaByMemberId(Long memberId) throws Exception {
		List<HistoryDharmaEntity> HistoryDharmaEntity = new ArrayList<>();
		try {
			HistoryDharmaEntity = historyDharmaRepository.getHistoryDhamaByMemberId(memberId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mapListEntityToDto(HistoryDharmaEntity);
	}

	@Override
	public List<HistoryDharmaDto> getAll() throws Exception {
		List<HistoryDharmaEntity> historyDharmaEntities = new ArrayList<>();

		historyDharmaEntities = historyDharmaRepository.findAll();
		log.info("getAll historyDharma >>" + historyDharmaEntities.toString());
		return mapListEntityToDto(historyDharmaEntities);
	}

	@Override
	public Integer delHistoryDhamaById(Long delId) throws Exception {
		Integer numberOfDelete;
		try {
			numberOfDelete = historyDharmaRepository.delHistoryDhamaById(delId);
			log.info("Delete historyDharma >> " + numberOfDelete + " Row.");
			return numberOfDelete;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public HistoryDharmaDto updateHistoryDhama(Long id, HistoryDharmaDto body) throws Exception {
		HistoryDharmaEntity historyDharmaConvert = mapDtoToEntity(body);
		HistoryDharmaEntity entity = new HistoryDharmaEntity();
		try {
			Optional<HistoryDharmaEntity> historyDharmaEntity = historyDharmaRepository.findById(id);
			if (!historyDharmaEntity.isPresent()) {
				return mapEntityToDto(historyDharmaEntity.get());
			}
			historyDharmaConvert.setHistoryDharmaId(id);
			historyDharmaConvert.setHistoryDharmaMemberId(historyDharmaEntity.get().getHistoryDharmaMemberId());
			entity = historyDharmaRepository.save(historyDharmaConvert);
			return mapEntityToDto(entity);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	private HistoryDharmaDto mapEntityToDto(HistoryDharmaEntity entity) throws Exception {
		HistoryDharmaDto dto = new HistoryDharmaDto();
		try {
			if (entity != null) {
				dto.setId(entity.getHistoryDharmaId());
				dto.setCourseName(entity.getHistoryDharmaDesc());
				dto.setLocation(entity.getHistoryDharmaLocation());
				dto.setMemberId(entity.getHistoryDharmaMemberId());
			}
			return dto;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	private List<HistoryDharmaDto> mapListEntityToDto(List<HistoryDharmaEntity> entities) throws Exception {
		List<HistoryDharmaDto> dtoList = new ArrayList<>();
		try {
			for (HistoryDharmaEntity entity : entities) {
				dtoList.add(mapEntityToDto(entity));
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dtoList;
	}

	private HistoryDharmaEntity mapDtoToEntity(HistoryDharmaDto dto) throws Exception {
		HistoryDharmaEntity entity = new HistoryDharmaEntity();
		try {
			entity.setHistoryDharmaDesc(dto.getCourseName());
			entity.setHistoryDharmaLocation(dto.getLocation());
			entity.setHistoryDharmaMemberId(dto.getMemberId());
			return entity;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

}
