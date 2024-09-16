package com.example.acidsofttesttask;

import org.springframework.boot.SpringApplication;

public class TestAcidSoftTestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.from(AcidSoftTestTaskApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
