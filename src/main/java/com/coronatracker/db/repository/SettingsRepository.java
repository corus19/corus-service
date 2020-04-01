package com.coronatracker.db.repository;

import com.coronatracker.db.model.Settings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sampath Katari on 26/03/20.
 */
@Repository
public interface SettingsRepository extends CrudRepository<Settings, String>{

}
