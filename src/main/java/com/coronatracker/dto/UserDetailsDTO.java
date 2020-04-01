package com.coronatracker.dto;

import com.coronatracker.db.model.CovidStatus;
import com.coronatracker.db.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Sampath Katari on 26/03/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    private String id;
    private String bluetoothSignature;
    private String deviceId;
    private String email;
    private String name;
    private CovidStatus covidContactStatus;
    private String country;
    private boolean sendContactDetails;
    private String os;
    private String osVersion;
    private String fcmId;

    public static UserDetailsDTO fromUser(final User user) {
        return UserDetailsDTO.builder()
                .id(user.getId())
                .bluetoothSignature(user.getBluetoothSignature())
                .deviceId(user.getDeviceId())
                .email(user.getEmail())
                .name(user.getName())
                .covidContactStatus(user.getCovidContactStatus())
                .country(user.getCountry())
                .sendContactDetails(user.isSendContactDetails())
                .os(user.getOs())
                .osVersion(user.getOsVersion())
                .fcmId(user.getFcmId())
                .build();
    }
}
