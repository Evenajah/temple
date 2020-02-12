package com.cdgs.temple.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.dto.TransportationDto;
import com.cdgs.temple.entity.TransportationEntity;
import com.cdgs.temple.entity.TransportationTimeEntity;
import com.cdgs.temple.repository.TransportationRepository;
import com.cdgs.temple.repository.TransportationTimeRepository;
import com.cdgs.temple.service.TransportationService;

@Service
public class TransportationServiceImpl implements TransportationService {

	private static final Logger log = LoggerFactory.getLogger(TransportationServiceImpl.class);

	@Autowired(required = true)
	TransportationRepository transportationRepository;

	@Autowired(required = true)
	TransportationTimeRepository transportationTimeRepository;

	/**
	 * getTransportationTempleName Description : this function get data of
	 * Transportation. Params : - create : 07/01/2563 By Thunya Phanmetharit
	 */
	@Override
	public List<TransportationDto> getTransportationTemple() {
		List<TransportationEntity> transportationEntity = new ArrayList<TransportationEntity>();
		try {
			transportationEntity = transportationRepository.findTranTemple();
		} catch (Exception e) {
			log.error("TransportationServiceImpl >>> getTransportationTemple : " + e.getMessage());
			e.printStackTrace();
		}
		return mapListEntityToDto(transportationEntity);
	}
	
	@Override
	public List<TransportationDto> getTransportationTempleForCreateCourse() {
		List<TransportationEntity> transportationEntity = new ArrayList<TransportationEntity>();
		try {
			transportationEntity = transportationRepository.findTranTempleForCreateCourse();
		} catch (Exception e) {
			log.error("TransportationServiceImpl >>> getTransportationTemple : " + e.getMessage());
			e.printStackTrace();
		}
		return mapListEntityToDto(transportationEntity);
	}
	
	@Override
	public TransportationDto getTransportationById(Long id) {
		TransportationEntity entity = new TransportationEntity();
		try {
			entity = transportationRepository.findById(id).get();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getTransportationById >>> " + e.getMessage());
		}
		return mapEntityToDto(entity);
	}
	
	public List<TransportationDto> getTransportationTemple(Long courseId) {
		List<TransportationEntity> transportationEntity = new ArrayList<TransportationEntity>();
		try {
			transportationEntity = transportationRepository.findTranTempleAndCourseId(courseId);
		} catch (Exception e) {
			log.error("TransportationServiceImpl >>> getTransportationTemple : " + e.getMessage());
			e.printStackTrace();
		}
		return mapListEntityToDto(transportationEntity);
	}
	
	/**
	 * getTransportationTemple
	 * Description : this function get data of Transportation Temple Course.
	 * Params : courseId 
	 * create : 15/01/2563
	 * By Natthakit Suk-on
	 */
	public List<TransportationDto> getTransportationTempleRegister(Long courseId) {
		List<TransportationEntity> transportationEntity = new ArrayList<TransportationEntity>();
		try {
			transportationEntity = transportationRepository.findTranTempleAndCourseIdRegister(courseId);
		} catch (Exception e) {
			log.error("TransportationServiceImpl >>> getTransportationTemple : " + e.getMessage());
			e.printStackTrace();
		}
		return mapListEntityToDto(transportationEntity);
	}

	/**
	 * getTransportationName Description : this function get data of Transportation.
	 * Params : - create : 23/09/2562 By Korawit Ratyiam
	 */
	public List<TransportationDto> getTransportationName() {
		List<TransportationEntity> transportationEntity = new ArrayList<TransportationEntity>();
		try {
			transportationEntity = transportationRepository.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mapListEntityToDto(transportationEntity);
	}

	/**
	 * createTransportation Description : this function insert data to DB. Params :
	 * transporatation : TransporatationDto create : 23/09/2562 By Korawit Ratyiam
	 * and Thunya Phanmetharit
	 */
	public TransportationDto createTransportation(TransportationDto transporatation) {
		TransportationEntity entity = new TransportationEntity();
		try {
			entity = transportationRepository.save(mapDtoToEntity(transporatation));
		} catch (Exception e) {
			log.error("CreateTransportation Error=>" + e.getMessage());
			e.printStackTrace();
		}
		return mapEntityToDto(entity);
	}

	/**
	 * updateTransportation Description : this function update data to DB in table
	 * transporatation and tran_time. Params : id : Long, transporatation :
	 * TransporatationDto create : 08/01/2563 By Waithaya Chouyanan
	 */
	public TransportationDto updateTransportationTemple(Long id, TransportationDto transportation) {
		TransportationEntity entity = new TransportationEntity();
		TransportationEntity tranEntity = new TransportationEntity();
		TransportationEntity tranEntityTemp = new TransportationEntity();
		TransportationTimeEntity tranTimeEntity = new TransportationTimeEntity();
		TransportationTimeEntity tranTimeEntityTemp = new TransportationTimeEntity();
		try {
			tranEntity = transportationRepository.findById(id).get();
			if (tranEntity.getTransportationTimeEntity() != null) {
				tranTimeEntityTemp = transportationTimeRepository.findById(tranEntity.getTransportationTimeEntity().getTransportationTimeId()).get();
			}
			tranTimeEntityTemp.setTransportationTimeId(tranEntity.getTransportationTimeId());
			if (transportation.getTimePickUp() != null) {
				tranTimeEntityTemp.setTransportationTempleTimePickup(transportation.getTimePickUp());
			}
			if (transportation.getTimeSend() != null) {
				tranTimeEntityTemp.setTransportationTempleTimeSend(transportation.getTimeSend());
			}
			tranTimeEntity = transportationTimeRepository.save(tranTimeEntityTemp);
			tranEntityTemp = mapDtoToEntity(transportation);
			tranEntityTemp.setTransportationTimeEntity(tranTimeEntity);
			if (transportation.getCourseId() != null) {
//				courseEntity.setCourseId(transportation.getCourseId());
//				tranEntityTemp.setCoursesEntity(courseEntity);
				tranEntityTemp.setTransportationCoursesId(transportation.getCourseId());
			}
			if (transportation.getName() != null) {
				tranEntityTemp.setTransportationName(transportation.getName());
			} else {
				tranEntityTemp.setTransportationName(tranEntity.getTransportationName());
			}
			tranEntityTemp.setTransportationTimeId(tranTimeEntity.getTransportationTimeId());
			if (transportation.getCourseId() != null) {
				tranEntityTemp.setTransportationCoursesId(transportation.getCourseId());
			}
			entity = transportationRepository.save(tranEntityTemp);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("UpdateTransportation Error=>" + e.getMessage());
		}
		return mapEntityToDto(entity);
	}

	/**
	 * updateTransportation Description : this function update data to DB. Params :
	 * id : Long, transporatation : TransporatationDto create : 23/09/2562 By
	 * Korawit Ratyiam
	 */
	public TransportationDto updateTransportation(Long id, TransportationDto transportation) {
		TransportationEntity entity = new TransportationEntity();
		TransportationEntity TransportationEntityTemp = new TransportationEntity();
		try {
			TransportationEntityTemp = transportationRepository.findById(id).get();
			TransportationEntityTemp.setTransportationName(transportation.getName());
			entity = transportationRepository.save(TransportationEntityTemp);
		} catch (Exception e) {
			log.error("UpdateTransportation Error=>" + e.getMessage());
		}
		return mapEntityToDto(entity);
	}

	/**
	 * deleteTransportation Description : this function update status of
	 * transportation from 1 to 0. Params : body : TransportationDto, id : Long
	 * create : 23/09/2562 By Korawit Ratyiam PS. : This controller can't not delete
	 * data because this data is foreign Key of Sensation
	 */
	public Boolean deleteTransportation(Long id, TransportationDto transportation) {
		try {
			if (id != null) {
				transportationRepository.deleteById(id);
				return true;
			}
		} catch (Exception e) {
			log.error("DeleteTransportation Error=>" + e.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * deleteTransportationTemple Description : this function is delete data
	 * transpotation Temple Params : id : TransportationId create : 08/01/2563 By
	 * Waithaya Chouyanan
	 */
	@Override
	public Boolean deleteTransportationTemple(Long id) {
		TransportationEntity transportationEntity = new TransportationEntity();
		try {
			if (id != null) {
				transportationEntity = transportationRepository.findById(id).get();
				transportationRepository.deleteById(id);
				transportationTimeRepository.deleteById(transportationEntity.getTransportationTimeId());
				return true;
			}
		} catch (Exception e) {
			log.error("deleteTransportationTemple Error=>" + e.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * gettransportationByCourseId Description : this function is SELECT transportation data By course ID
	 * Params : course Id create : 13/01/2563 By Waithaya Chouyanan
	 */
	@Override
	public TransportationDto getTransportationByCourseId(Long courseId) {
		TransportationEntity entity = new TransportationEntity();
		try {
			entity = transportationRepository.findByCourseId(courseId);
		} catch (Exception e) {
			log.error("getTransportationByCourseId >> " + e.getMessage());
		}
		return mapEntityToDto(entity);
	}
	
	/**
	 * mapListEntityToDto Description : this function is mapping list data of Entity
	 * to Dto. Params : id : Long, transporatation : TransporatationDto create :
	 * 23/09/2562 By Korawit Ratyiam
	 */
	private List<TransportationDto> mapListEntityToDto(List<TransportationEntity> entities) {
		List<TransportationDto> dtoList = new ArrayList<TransportationDto>();
		if (entities != null) {
			for (TransportationEntity entity : entities) {
				dtoList.add(mapEntityToDto(entity));
			}
		}
		return dtoList;
	}

	/**
	 * mapEntityToDto Description : this function is mapping data of Entity to Dto.
	 * Params : entity : TransportationEntity create : 23/09/2562 By Korawit Ratyiam
	 */
	private TransportationDto mapEntityToDto(TransportationEntity entity) {
		TransportationDto dto = new TransportationDto();
		if (entity != null) {
			dto.setId(entity.getTransportationId());
			dto.setName(entity.getTransportationName());
			if (entity.getTransportationTimeEntity() != null) {
				dto.setTranTimeId(entity.getTransportationTimeEntity().getTransportationTimeId());
				dto.setTimePickUp(entity.getTransportationTimeEntity().getTransportationTempleTimePickup());
				dto.setTimeSend(entity.getTransportationTimeEntity().getTransportationTempleTimeSend());
			}
//			if (entity.getCoursesEntity() != null) {
//				dto.setCourseId(entity.getCoursesEntity().getCourseId());
//			}
		}
		return dto;
	}

	/**
	 * mapEntityToDto Description : this function is mapping data of Dto to Entity.
	 * Params : dto : TransportationDto create : 23/09/2562 By Korawit Ratyiam
	 */
	private TransportationEntity mapDtoToEntity(TransportationDto dto) {
		TransportationEntity entity = new TransportationEntity();
		TransportationTimeEntity tranTimeEntity = new TransportationTimeEntity();
		entity.setTransportationId(dto.getId());
		entity.setTransportationName(dto.getName());
		if (dto.getTimePickUp() != null && dto.getTimeSend() != null) {
			tranTimeEntity.setTransportationTimeId(dto.getTranTimeId());
			entity.setTransportationTimeEntity(tranTimeEntity);
			entity.setTransportationTimeId(dto.getTranTimeId());
		}
		if (dto.getCourseId() != null) {
//			courseEntity.setCourseId(dto.getCourseId());
//			entity.setCoursesEntity(courseEntity);
			entity.setTransportationCoursesId(dto.getCourseId());
		}
		return entity;
	}

	@Override
	public TransportationDto getTransportationTempleMember(Long courseId, Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}
	

	/**
	 * mapOptionToentity Description : this function is mapping data of Optional to
	 * Entity. Params : opEntity : Optional<TransportationEntity> create :
	 * 23/09/2562 By Korawit Ratyiam
	 */
//	 private TransportationEntity mapOptionToentity(Optional<TransportationEntity> opEntity) {
//		 TransportationEntity entity = new TransportationEntity();
//		 TransportationTimeEntity tranTimeEntity = new TransportationTimeEntity();
//		 CourseEntity courseEntity = new CourseEntity();
//		 
//		 if(opEntity != null) {
//			 entity.setTransportationId(opEntity.get().getTransportationId());
//			 entity.setTransportationName(opEntity.get().getTransportationName());
//			 entity.setTranTimeId(opEntity.get().getTranTimeId());
//			 entity.setCoursesId(opEntity.get().getCoursesId());
////			 tranTimeEntity.setTransportationTimeId(opEntity.get().getTransportationTimeEntity().getTransportationTimeId());
////			 entity.setTransportationTimeEntity(tranTimeEntity);
////			 courseEntity.setCourseId(opEntity.get().getCoursesEntity().getCourseId());
////			 entity.setCoursesEntity(courseEntity);
//		 }
//		 return entity;
//	 }

}
