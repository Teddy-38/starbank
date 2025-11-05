package starbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCachin
public class StarbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarBankApplication.class, args);
	}
}