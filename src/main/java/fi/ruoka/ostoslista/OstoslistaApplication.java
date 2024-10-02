package fi.ruoka.ostoslista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class OstoslistaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OstoslistaApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		System.out.println("moi");
		return "Greetings from Spring Boot inside Docker container, hot reload!";
	}

}
