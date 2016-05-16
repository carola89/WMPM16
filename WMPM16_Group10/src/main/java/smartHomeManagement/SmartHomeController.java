package smartHomeManagement;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import smartHomeManagement.models.ColdWaterConsumption;
import smartHomeManagement.models.Customer;
import smartHomeManagement.repositories.ColdWaterRepository;
import smartHomeManagement.repositories.CustomerRepository;

@SpringBootApplication
public class SmartHomeController {

	private static final Logger log = LoggerFactory.getLogger(SmartHomeController.class);

	public static void main(String[] args) {
		SpringApplication.run(SmartHomeController.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Customer(1, "Jack Bauer", "test@wmpm.com", "12345", "Schottenring 5"));
			repository.save(new Customer(2, "Chloe O'Brian", "test@wmpm.com", "678943", "Schottenring 6"));
			
			// fetch all customers
			log.info("Customers found with findAll():");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}

			// fetch customers by last name
			log.info("Customer found with findByName('Jack Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : repository.findByName("Jack Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}
	
	@Bean
	public CommandLineRunner demo2(ColdWaterRepository repository) {
		return (args) -> { 
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-DD");
			//save a couple of ColdwaterConsumption
			repository.save(new ColdWaterConsumption(1, 123456, 123, dateFormat.parse("2016-05-13"), 1));
			repository.save(new ColdWaterConsumption(2, 123456, 124, dateFormat.parse("2016-05-14"), 2));
			repository.save(new ColdWaterConsumption(3, 123456, 125, dateFormat.parse("2016-05-15"), 3));
		};
		
	}
}
