package com.cdgs.temple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cdgs.temple.entity.TransportationEntity;

public interface TransportationRepository extends CrudRepository<TransportationEntity, Long> {
	
	@Query(value ="SELECT * FROM transportations where tran_time_id IS NULL", nativeQuery = true)
	List<TransportationEntity> findAll();
	
	@Query(value ="SELECT * FROM temple.transportations order by tran_time_id DESC", nativeQuery = true)
	List<TransportationEntity> findAllOrderBy();
	
	@Query(value = "SELECT t.tran_id, t.tran_name, t.tran_time_id, t.course_id, tt.tran_time_pickup, tt.tran_time_send "
			+ "FROM transportations t " + "LEFT JOIN transportations_time tt " + "ON t.tran_time_id = tt.tran_time_id "
			+ "WHERE t.tran_time_id IS NOT NULL ", nativeQuery = true)
	List<TransportationEntity> findTranTemple();
	
	@Query(value = "SELECT t.tran_id, t.tran_name, t.tran_time_id, t.course_id, tt.tran_time_pickup, tt.tran_time_send "
			+ "FROM transportations t " + "LEFT JOIN transportations_time tt " + "ON t.tran_time_id = tt.tran_time_id "
			+ "WHERE t.tran_time_id IS NOT NULL " + "AND t.course_id IS  NULL", nativeQuery = true)
	List<TransportationEntity> findTranTempleForCreateCourse();
	
	@Query(value = "SELECT t.tran_id, t.tran_name, t.tran_time_id, t.course_id, tt.tran_time_pickup, tt.tran_time_send "
			+ "FROM transportations t " + "LEFT JOIN transportations_time tt " + "ON t.tran_time_id = tt.tran_time_id "
			+ "WHERE t.tran_time_id IS NOT NULL AND (t.course_id IS NULL OR t.course_id = :courseId)", nativeQuery = true)
	List<TransportationEntity> findTranTempleAndCourseId(@Param("courseId") Long courseId);
	
	@Query(value = "SELECT t.tran_id, t.tran_name, t.tran_time_id, t.course_id, tt.tran_time_pickup, tt.tran_time_send "
			+ "FROM transportations t " + "LEFT JOIN transportations_time tt " + "ON t.tran_time_id = tt.tran_time_id "
			+ "WHERE t.tran_time_id IS NOT NULL AND t.course_id = :courseId", nativeQuery = true)
	List<TransportationEntity> findTranTempleAndCourseIdRegister(@Param("courseId") Long courseId);
	
	
	@Query(value = "SELECT t.tran_id, t.tran_name, t.tran_time_id, t.course_id, tt.tran_time_pickup, tt.tran_time_send "
			+ "FROM transportations t "
			+ "LEFT JOIN transportations_time tt "
			+ "ON t.tran_time_id = tt.tran_time_id "
			+ "WHERE t.tran_time_id IS NOT NULL AND t.course_id = :courseId", nativeQuery = true)
	TransportationEntity findByCourseId(@Param("courseId") Long courseId);

}
