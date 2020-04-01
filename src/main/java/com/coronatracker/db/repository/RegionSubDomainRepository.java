package com.coronatracker.db.repository;

import com.coronatracker.db.model.RegionSubDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionSubDomainRepository extends CrudRepository<RegionSubDomain, String> {

}
