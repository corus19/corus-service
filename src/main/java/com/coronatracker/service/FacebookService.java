package com.coronatracker.service;

import com.coronatracker.dto.FacebookUserDetailsDTO;
import com.coronatracker.exceptions.InvalidFbTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Sampath Katari on 26/03/20.
 */

@Slf4j
@Service
public class FacebookService {

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${fbUrl}")
    private String fbUrl;

    public FacebookUserDetailsDTO verifyWithFacebook(final String oAuthToken) {
        final Request request =new Request.Builder()
                .url(fbUrl+oAuthToken)
                .build();
        try(Response response = okHttpClient.newCall(request).execute()) {
            final String responseString = response.body().string();
            return objectMapper.readValue(responseString, FacebookUserDetailsDTO.class);
        } catch(final Exception ex) {
            log.error("Malformed access token");
            throw new InvalidFbTokenException("Invalid Facebook auth token");
        }
    }
}
