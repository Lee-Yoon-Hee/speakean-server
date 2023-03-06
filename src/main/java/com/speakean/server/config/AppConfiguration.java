package com.speakean.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.speakean.server.service.ETRI;


@Configuration
public class AppConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        return JsonMapper.builder()
        		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        		.build();
    }
    
    @Bean
    ETRI etri(ObjectMapper objectMapper) {
    	return new ETRI(objectMapper);
    }
}
