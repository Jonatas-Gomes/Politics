package br.com.compass.associate.framework.helper.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;

public class JacksonFactory {
    private static ObjectMapper objectMapper = null;
    @Bean
    public static ObjectMapper getObjectMapper() {
        if(objectMapper == null){
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return objectMapper;
    }
}
