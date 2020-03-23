package ru.liga.intership.badcode.db;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import ru.liga.intership.badcode.BadcodeApplication;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.service.PersonService;

import java.util.ArrayList;
import java.util.List;

public class PersonServiceTest {

    @BeforeClass
    public static void beforeTest() {
        SpringApplication.run(BadcodeApplication.class);
    }

    @Test
    public void adultMaleUsersAverageBMITest() {
        Assertions.assertThat(new PersonService().getAdultMaleUsersAverageBMI())
                .as("Возвращенно некорректное значение")
                .isEqualTo(25.774209960992707);
    }

    @Test
    public void massIndexCalculationTest() {
        List<Person> personList = new ArrayList<Person>() {
            {
                Person person1 = new Person();
                person1.setId(1L);
                person1.setSex("male");
                person1.setName("Kolya");
                person1.setHeight(180L);
                person1.setWeight(80L);
                person1.setAge(18L);

                Person person2 = new Person();
                person2.setId(1L);
                person2.setSex("male");
                person2.setName("Kolya");
                person2.setHeight(200L);
                person2.setWeight(102L);
                person2.setAge(18L);

                add(person1);
                add(person2);
            }
        };

        Assertions.assertThat(new PersonService().massIndexCalculation(personList))
                .as("Возвращенно некорректное значение")
                .isEqualTo(25.095679012345677);
    }

    @Test
    public void massIndexCalculationTestEmpty() {
        List<Person> personList = new ArrayList<>();

        Assertions.assertThat(new PersonService().massIndexCalculation(personList))
                .as("Возвращенно некорректное значение")
                .isNaN();
    }

}
