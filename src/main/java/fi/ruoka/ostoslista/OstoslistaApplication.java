package fi.ruoka.ostoslista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OstoslistaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OstoslistaApplication.class, args);
	}

}