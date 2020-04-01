package com.coronatracker.notification;

import com.coronatracker.db.model.User;
import com.coronatracker.notification.fcm.FCMHandler;
import com.coronatracker.notification.model.NotificationError;
import com.coronatracker.notification.model.NotificationType;
import com.coronatracker.service.RegionUrlService;
import com.coronatracker.service.SettingsService;
import com.coronatracker.service.UserService;
import com.coronatracker.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {


	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	@Autowired
	private FCMHandler fcmHandler;

	@Autowired
	private UserService userService;

	@Autowired
	private RegionUrlService regionUrlService;

	@Autowired
	private SettingsService settingsService;


	@Autowired
	private ObjectMapper objectMapper;

	public List<NotificationError> sendSettingsNotification(List<String> userIds) {

		final List<User> users = userService.getById(userIds);
		return users.stream().map(this::sendSettingsNotification)
			.collect(Collectors.toList());
	}


	public List<NotificationError> sendNotification(final String notification,
		final NotificationType notificationType,
		final List<String> userIds) {
		return userService.getById(userIds)
			.stream()
			.map(user -> sendNotification(user.getId(), user.getFcmId(),
				notification, notificationType))
			.collect(Collectors.toList());
	}

	private NotificationError sendSettingsNotification(final User user) {

		final String regionUrl = regionUrlService.getRegionUrl(user.getCountry());
		final String diseaseStatus = user.getCovidContactStatus().name();
		final Map<String, String> allSettings = settingsService.getAllSettings();

		final Map<String, String> notificationSettings = new HashMap<>(allSettings);
		notificationSettings
			.put(Constants.SEND_CONTACT_DETAILS_SETTINGS_TAG,
				Boolean.toString(user.isSendContactDetails()));
		notificationSettings.put(Constants.REGION_URL_TAG, regionUrl);
		notificationSettings.put(Constants.DISEASE_STATUS_TAG, diseaseStatus);

		try {
			String notificationStr = objectMapper.writeValueAsString(notificationSettings);
			return sendNotification(user.getId(), user.getFcmId(), notificationStr,
				NotificationType.SETTINGS);

		} catch (JsonProcessingException e) {
			logger.error(MessageFormat
				.format("Error in parsing notification settings. User: {0}, Notification: {1}",
					user.getId(), notificationSettings));

			return NotificationError.builder()
				.errorCode(2)
				.errorMessage(e.getMessage())
				.build();
		}
	}

	private NotificationError sendNotification(final String userId, final String fcmToken,
		final String notificationStr, final NotificationType notificationType) {

		final NotificationError.NotificationErrorBuilder notificationErrorBuilder = NotificationError
			.builder()
			.userId(userId);
		try {
			String response = fcmHandler
				.sendNotification(notificationStr, fcmToken, notificationType);
			notificationErrorBuilder
				.errorCode(0)
				.fcmResponse(response);
		} catch (FirebaseMessagingException e) {
			logger.error(MessageFormat.format("Error in sending message. User id: {0}",
				userId), e);
			notificationErrorBuilder
				.errorMessage(e.getMessage())
				.errorCode(1);
		}
		return notificationErrorBuilder.build();
	}
}
