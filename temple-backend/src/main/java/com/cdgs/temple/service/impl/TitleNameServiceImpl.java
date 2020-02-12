package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.TitleNameDto;
import com.cdgs.temple.entity.TitleNamesEntity;
import com.cdgs.temple.repository.TitleNameRepository;
import com.cdgs.temple.service.TitleNameService;

@Service
public class TitleNameServiceImpl implements TitleNameService {

	private static final Logger log = LoggerFactory.getLogger(TitleNameServiceImpl.class);

	@Autowired(required = true)
	TitleNameRepository titleNameRepository;

	@Override
	public List<TitleNameDto> getTitleNames() {
		List<TitleNamesEntity> titleNameEntities = new ArrayList<TitleNamesEntity>();
		try {
			titleNameEntities = titleNameRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return convListEntityToDto(titleNameEntities);
	}

	@Override
	public TitleNameDto getTitleName(Long id) {
		log.info("getTitleName : Start");
		TitleNamesEntity entity = new TitleNamesEntity();
		try {
			entity = convOptionalToEntity(titleNameRepository.findById(id));
		} catch (Exception e) {
			log.error("getTitleName Error=>" + e.getMessage());
		}
		log.info("getTitleName : End");
		return convEntityToDto(entity);
	}

	@Override
	public TitleNameDto updateTitleName(Long id, TitleNameDto titlename) {
		TitleNamesEntity titlenameData = convDtoToEntity(titlename);
		TitleNamesEntity entity = new TitleNamesEntity();
		try {
			Optional<TitleNamesEntity> titleNameEntity = titleNameRepository.findById(id);
			if (!titleNameEntity.isPresent()) {
				return convEntityToDto(convOptionalToEntity(titleNameEntity));
			}
			titlenameData.setTitleId(id);
			entity = titleNameRepository.save(titlenameData);
		} catch (Exception e) {
			log.error("getTitleName Error=>" + e.getMessage());
			log.error("getTitlename value => " + titlename.toString());
		}
		return convEntityToDto(entity);
	}

	@Override
	public TitleNameDto createTitleName(TitleNameDto titlename) {
		TitleNamesEntity titlenameData = convDtoToEntity(titlename);
		TitleNamesEntity entity = new TitleNamesEntity();
		try {
			if (titlename != null) {
				entity = titleNameRepository.save(titlenameData);
			}
		} catch (Exception e) {
			log.error("getTitleName Error=>" + e.getMessage());
		}
		return convEntityToDto(entity);
	}

	@Override
	public Boolean deleteTitleName(Long id) {
		try {
			if (id != null) {
				titleNameRepository.deleteById(id);
				return true;
			}
		} catch (Exception e) {
			log.error("getTitleName Error=>" + e.getMessage());
			return false;
		}
		return true;
	}

	private TitleNamesEntity convDtoToEntity(TitleNameDto titlename) {
		TitleNamesEntity entity = new TitleNamesEntity();
		entity.setTitleId(titlename.getId());
		entity.setTitleDisplay(titlename.getDisplay());
		entity.setTitleName(titlename.getName());
		return entity;
	}

	private TitleNamesEntity convOptionalToEntity(Optional<TitleNamesEntity> entity) {
		TitleNamesEntity dto = new TitleNamesEntity();
		if (entity != null) {
			dto.setTitleDisplay(entity.get().getTitleDisplay());
			dto.setTitleId(entity.get().getTitleId());
			dto.setTitleName(entity.get().getTitleName());
		}
		return dto;
	}

	private List<TitleNameDto> convListEntityToDto(List<TitleNamesEntity> entities) {
		List<TitleNameDto> dtoList = new ArrayList<TitleNameDto>();
		if (entities != null) {
			for (TitleNamesEntity entity : entities) {
				dtoList.add(convEntityToDto(entity));
			}
		}

		return dtoList;
	}

	private TitleNameDto convEntityToDto(TitleNamesEntity entity) {
		TitleNameDto dto = new TitleNameDto();
		if (entity != null) {
			dto.setId(entity.getTitleId());
			dto.setDisplay(entity.getTitleDisplay());
			dto.setName(entity.getTitleName());
		}
		return dto;
	}

	@Override
	public String toString() {
		return "TitleNameServiceImpl [titleNameRepository=" + titleNameRepository + "]";
	}

}
