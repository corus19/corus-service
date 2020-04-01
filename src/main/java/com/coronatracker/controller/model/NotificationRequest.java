package com.coronatracker.controller.model;

import com.coronatracker.notification.model.NotificationType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

	private List<String> userIds;
	private NotificationType notificationType;
	private String message;

}
