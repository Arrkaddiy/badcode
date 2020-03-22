package ru.liga.intership.badcode.db;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import ru.liga.intership.badcode.BadcodeApplication;
import ru.liga.intership.badcode.service.PersonService;

public class PersonServiceTest {

    @BeforeClass
    public static void beforeTest() {
        SpringApplication.run(BadcodeApplication.class);
    }

    @Test
    public void test() {
        PersonService personService = new PersonService();
        personService.getDataFromDB();
    }

}
