package com.materials.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "material.queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }
}

