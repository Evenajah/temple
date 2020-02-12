package com.cdgs.temple.repository;

import com.cdgs.temple.dto.NotificationsDto;
import com.cdgs.temple.util.FirebaseConnection;
import com.google.cloud.firestore.Firestore;

public class NotificationsRepository {
	private Firestore database = new FirebaseConnection().getFirestoreDatabase();
	
	public void createUserNotification(NotificationsDto body) {
		try {
			database.collection("notification").add(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}