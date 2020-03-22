package ru.liga.intership.badcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.liga.intership.badcode.db.DBConnection;
import ru.liga.intership.badcode.properties.PropertiesReader;
import ru.liga.intership.badcode.service.PersonService;

import java.util.Properties;

@SpringBootApplication
public class BadcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BadcodeApplication.class, args);
		PersonService personService = new PersonService();
		personService.getAdultMaleUsersAverageBMI();
	}
}
