package com.materials.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.materials.school.entity")
@EnableJpaRepositories("com.materials.school.repository")
public class MaterialsSchoolRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaterialsSchoolRabbitmqApplication.class, args);

	}

}
