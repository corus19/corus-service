package com.coronatracker.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Entity
public class RegionSubDomain {

	@Id
	@Column
	private String country;
	@Column
	private String subDomain;

}
