package com.cdgs.temple.repository;

import java.util.List;
import com.cdgs.temple.entity.LockerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerRepository extends CrudRepository<LockerEntity, Long> {

	List<LockerEntity> findAllByEnableIsTrue();

	List<LockerEntity> findAllByEnableIsTrueAndIsActive(char isActive);

	LockerEntity findAllByLockerId(long lockerId);

	LockerEntity findByLockerIdAndEnableIsTrue(long lockerId);

	LockerEntity findByLockerId(long lockerId);

	int countByLocationIdAndLockerNumber(long locationId, String lockerNumber);

}
