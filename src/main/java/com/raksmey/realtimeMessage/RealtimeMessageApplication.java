package com.raksmey.realtimeMessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class RealtimeMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeMessageApplication.class, args);
	}

}
