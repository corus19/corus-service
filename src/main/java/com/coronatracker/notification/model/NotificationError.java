package com.coronatracker.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationError {

	private String fcmResponse;
	private int errorCode;
	private String errorMessage;
	private String userId;
}
