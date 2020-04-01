package com.coronatracker.dto;

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
public class LoginDTO {
    private String fbToken;
}
