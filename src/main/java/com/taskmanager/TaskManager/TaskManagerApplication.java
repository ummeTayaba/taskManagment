package com.taskmanager.TaskManager;

import com.taskmanager.TaskManager.service.AppStartupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(TaskManagerApplication.class, args);

		AppStartupService appStartupService = (AppStartupService) app.getBean("appStartupService");
		appStartupService.insertDummyUsers();
	}

}
