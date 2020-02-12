package com.cdgs.temple.service;

import java.util.List;

public interface NotificationsService {
	void createMonkNotifications(List<Long> listTeacherId, Long specialApproveId, Long courseOutTimeId,
			String specialApproveStatus, String courseOutTimeName);
	
	void createUserNotifications(Long userId, Long specialApproveId, Long courseOutTimeId, String specialApproveStatus,
			String courseOutTimeName);
}
