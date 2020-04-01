package com.coronatracker.controller;

import com.coronatracker.controller.model.NotificationRequest;
import com.coronatracker.notification.NotificationService;
import com.coronatracker.notification.model.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sampath Katari on 26/03/20.
 */

@RestController
@RequestMapping("/internal")
public class InternalController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping(value = "/notification/message")
	public ResponseEntity sendNotification(@RequestBody NotificationRequest notificationRequest) {

		if (notificationRequest.getNotificationType() == NotificationType.SETTINGS) {
			return ResponseEntity
				.ok(notificationService.sendSettingsNotification(notificationRequest.getUserIds()));
		}
		return ResponseEntity.ok(notificationService
			.sendNotification(notificationRequest.getMessage(),
				notificationRequest.getNotificationType(), notificationRequest.getUserIds()));
	}

}