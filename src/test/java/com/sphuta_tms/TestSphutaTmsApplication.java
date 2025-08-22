package com.sphuta_tms;

import org.springframework.boot.SpringApplication;

public class TestSphutaTmsApplication {

	public static void main(String[] args) {
		SpringApplication.from(SphutaTmsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
