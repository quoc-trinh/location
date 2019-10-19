package com.quizz.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@SpringBootApplication
public class LocationApplication {


	@Autowired
	private MongoMappingContext mongoMappingContext;

	public static void main(String[] args) {
		SpringApplication.run(LocationApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initIndicesAfterStartup() {
		mongoMappingContext.setAutoIndexCreation(true);
	}
	@Bean
	public MongoMappingContext springDataMongoMappingContext() {
		return new MongoMappingContext();
	}
}
