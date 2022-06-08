package com.jiren.shared.configuration;

import com.jiren.shared.guid.GUIDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GuidConfig {
    @Bean
    public GUIDGenerator guidGenerator() {
        return new GUIDGenerator(1, 99);
    }
}
