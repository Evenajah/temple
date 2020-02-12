package com.cdgs.temple.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cdgs.temple.entity.GenderEntity;

@Repository
public interface GenderRepository extends CrudRepository<GenderEntity, Long> {

}
