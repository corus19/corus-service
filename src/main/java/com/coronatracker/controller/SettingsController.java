package com.coronatracker.controller;

import com.coronatracker.dto.SettingsRequestDTO;
import com.coronatracker.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sampath Katari on 25/03/20.
 *
 * Settings controller needs only one api to  get the associated value for a given key.
 * Assuming Create, Update and Delete will be taken care
 */
@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @PostMapping("")
    public ResponseEntity getSettings(@RequestBody final SettingsRequestDTO settingsRequestDTO) {
        return ResponseEntity.ok(settingsService.getByKey(settingsRequestDTO));
    }
}
