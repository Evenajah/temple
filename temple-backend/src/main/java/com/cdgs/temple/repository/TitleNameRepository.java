package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cdgs.temple.entity.TitleNamesEntity;

public interface TitleNameRepository extends CrudRepository<TitleNamesEntity, Long> {

	List<TitleNamesEntity> findAll();

	// TitleNamesEntity findTitleName(@Param(value="titleId") Long titleId);
}
