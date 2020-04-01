package com.coronatracker.notification.fcm;

import com.coronatracker.notification.model.NotificationType;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class FCMHandler {

	private static final String FIREBASE_DATABASE_URL = "https://corus-96e40.firebaseio.com";


	private final FirebaseApp firebaseApp;

	public FCMHandler() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials
						.fromStream(new ClassPathResource("/corus-96e40-firebase-adminsdk.json").getInputStream()))
				.setDatabaseUrl(FIREBASE_DATABASE_URL).build();
		firebaseApp = FirebaseApp.initializeApp(options);
	}

	public String sendNotification(String notification, String fcmToken,
		NotificationType notificationType)
		throws FirebaseMessagingException {

		Message message = Message.builder()
			.putData("data", notification)
			.putData("type", notificationType.toString())
			.setToken(fcmToken)
			.build();

		String response = FirebaseMessaging.getInstance().send(message);

		return response;
	}
}
