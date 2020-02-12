package com.cdgs.temple.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseConnection {
	private static final Logger log = LoggerFactory.getLogger(FirebaseConnection.class);

	public FirebaseConnection() {
		initialFirebase();
	}

	private void initialFirebase() {
		// Fetch the service account key JSON file contents
		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream("temple-b0781-firebase-adminsdk-xiewa-59f4edd16e.json");
			// Initialize the app with a service account, granting admin privileges
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://temple-b0781.firebaseio.com/").build();
			FirebaseApp firebaseApp = null;
			List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
			if (firebaseApps != null && !firebaseApps.isEmpty()) {
				for (FirebaseApp app : firebaseApps) {
					if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
						firebaseApp = app;
				}
			} else {
				firebaseApp = FirebaseApp.initializeApp(options);
			}
		} catch (IOException e) {
			log.error("catch >> initialFirebase ", e);
		}
	}

	public Firestore getFirestoreDatabase() {
		Firestore db = FirestoreClient.getFirestore();
		return db;
	}
}
