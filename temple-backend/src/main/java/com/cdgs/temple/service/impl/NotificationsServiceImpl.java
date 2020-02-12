package com.cdgs.temple.service.impl;

import java.util.Calendar;
import java.util.List;

import com.cdgs.temple.dto.NotificationsDto;
import com.cdgs.temple.repository.NotificationsRepository;
import com.cdgs.temple.service.NotificationsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationsServiceImpl implements NotificationsService {

	private final NotificationsRepository notificationsRepository = new NotificationsRepository();

	public NotificationsServiceImpl() {
		super();
	}

	@Override
	public void createMonkNotifications(List<Long> listTeacherId, Long specialApproveId, Long courseOutTimeId,
			String specialApproveStatus, String courseName) {
		try {
			for (Long teacherId : listTeacherId) {
				NotificationsDto notificationDto = new NotificationsDto();
				notificationDto.setSpecialApproveID(specialApproveId);
				notificationDto.setCourseID(courseOutTimeId);
				notificationDto.setMemberID(teacherId);
				notificationDto.setSpecialApproveStatus(specialApproveStatus);
				notificationDto.setNotificationStatus(Long.parseLong("0"));
				notificationDto.setDetail(courseName);
				notificationDto.setNotificationTime(Calendar.getInstance().getTime());
				notificationsRepository.createUserNotification(notificationDto);
			}

		} catch (Exception e) {
			log.error("createMonkNotifications", e);
		}
	}

	@Override
	public void createUserNotifications(Long userId, Long specialApproveId, Long courseId, String specialApproveStatus,
			String courseName) {
		try {
			NotificationsDto notificationDto = new NotificationsDto();
			notificationDto.setSpecialApproveID(specialApproveId);
			notificationDto.setCourseID(courseId);
			notificationDto.setMemberID(userId);
			notificationDto.setSpecialApproveStatus(specialApproveStatus);
			notificationDto.setNotificationStatus(Long.parseLong("0"));
			notificationDto.setDetail(courseName);
			notificationDto.setNotificationTime(Calendar.getInstance().getTime());
			notificationsRepository.createUserNotification(notificationDto);
		} catch (Exception e) {
			log.error("error createUserNotifications() ", e);
		}
	}

}
