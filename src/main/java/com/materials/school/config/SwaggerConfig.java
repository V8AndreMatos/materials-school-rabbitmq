package com.materials.school.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("School Materials API")
                        .version("1.0")
                        .description("API for managing school materials")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("dev@school.com")
                                .url("https://school.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));

    }
}

