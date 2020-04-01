package com.coronatracker.service;

import com.coronatracker.db.model.Settings;
import com.coronatracker.db.repository.SettingsRepository;
import com.coronatracker.dto.SettingsRequestDTO;
import com.coronatracker.util.Constants;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Sampath Katari on 26/03/20.
 */
@Service
public class SettingsService {
    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private RegionUrlService regionUrlService;

    /**
     * Get Settings {@link Settings} for a given (key/id)
     * @param settingsRequestDTO
     * @return
     */
    public Map<String, String> getByKey(final SettingsRequestDTO settingsRequestDTO) {
        final Iterable<Settings> allById = settingsRepository.findAllById(settingsRequestDTO.getKeys());
        final Map<String, String> settingsKeyValue = new HashMap<>();
        allById.forEach(settings -> {
            settingsKeyValue.put(settings.getId(), settings.getValue());
        });

        if(!StringUtils.isEmpty(settingsRequestDTO.getCountry())) {
            String regionUrl = regionUrlService.getRegionUrl(settingsRequestDTO.getCountry());
            settingsKeyValue.put(Constants.REGION_URL_TAG, regionUrl);
        }
        return settingsKeyValue;
    }


    public Map<String, String> getAllSettings() {
        final Map<String, String> settingKeyValue = new HashMap<>();
        for(Settings settings: settingsRepository.findAll()) {
            settingKeyValue.put(settings.getId(), settings.getValue());
        }

        return settingKeyValue;
    }
}
