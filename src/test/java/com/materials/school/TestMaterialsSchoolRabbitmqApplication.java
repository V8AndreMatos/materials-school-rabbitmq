package com.materials.school;

import org.springframework.boot.SpringApplication;

public class TestMaterialsSchoolRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.from(MaterialsSchoolRabbitmqApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
