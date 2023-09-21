package com.example.demo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkoutLogApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(WorkoutLogApplication.class, args);
	}

}
