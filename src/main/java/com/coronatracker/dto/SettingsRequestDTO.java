package com.coronatracker.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sampathkatari on 27/03/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsRequestDTO {
    private List<String> keys;
    private String country;
}
