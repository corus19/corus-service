package com.coronatracker.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Sampath Katari on 27/03/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkContactDetailsDTO {
    private List<ContactDetailsDTO> deviceRequests;
}
