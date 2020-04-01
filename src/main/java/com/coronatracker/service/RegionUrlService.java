package com.coronatracker.service;

import com.coronatracker.db.model.RegionSubDomain;
import com.coronatracker.db.repository.RegionSubDomainRepository;
import java.text.MessageFormat;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RegionUrlService {

	private final String defaultEndpoint;
	private final String subDomainEndpointFormat;

	@Autowired
	private RegionSubDomainRepository regionSubDomainRepository;

	public RegionUrlService(@Value("${endpoint}") String defaultEndpoint,
		@Value("${subDomainEndpointFormat}") String subDomainEndpointFormat) {

		this.defaultEndpoint = defaultEndpoint;
		this.subDomainEndpointFormat = subDomainEndpointFormat;
	}

	public String getRegionUrl(String country) {

		Optional<RegionSubDomain> regionSubDomain = regionSubDomainRepository.findById(country
			.toLowerCase());

		return regionSubDomain
			.map(subDomain -> MessageFormat
				.format(subDomainEndpointFormat, subDomain.getSubDomain()))
			.orElse(defaultEndpoint);
	}
}
