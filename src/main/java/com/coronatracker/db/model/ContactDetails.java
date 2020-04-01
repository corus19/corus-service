package com.coronatracker.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"from_user_id", "to_user_id", "dateTime"}))
public class ContactDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUserId;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
    private long dateTime;

    @Column
    private long rssi;

    @Column
    private long txPower;
}
