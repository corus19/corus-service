package com.coronatracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Sampath Katari on 25/03/20.
 */
@SpringBootApplication
@ComponentScan("com.coronatracker")
public class CoronaTracker {
    public static void main(String[] args) {
        SpringApplication.run(CoronaTracker.class, args);
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
