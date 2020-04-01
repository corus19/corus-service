package com.coronatracker.db.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 * Created by Sampath Katari on 26/03/20.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private String bluetoothSignature;

    @Column
    private String deviceId;

    @Column
    private String fcmId;

    @Column
    private String email;

    @Column
    private String fbAuthToken;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private CovidStatus covidContactStatus;

    @Column
    private LocalDateTime lastUpdatedTime;

    @Column
    private String country;

    @Column
    private boolean sendContactDetails;

    @Column
    private String os;

    @Column
    private String osVersion;

}
