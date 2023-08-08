package com.backend.TravelGuide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TravelGuideApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelGuideApplication.class, args);
	}

}
