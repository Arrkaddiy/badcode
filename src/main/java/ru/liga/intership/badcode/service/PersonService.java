package ru.liga.intership.badcode.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.db.PersonDao;
import ru.liga.intership.badcode.domain.Person;

import java.util.List;


public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    /**
     * Возвращает средний индекс массы тела всех лиц мужского пола старше 18 лет
     */
    public double getAdultMaleUsersAverageBMI() {
        log.info("Рассчет среднего индекса массы тела всех лиц мужского пола старше 18 лет");
        PersonDao personsDao = new PersonDao();
        List<Person> adultPersons = personsDao.getPersonsMenOverEighteenOld();
        double massIndex = massIndexCalculation(adultPersons);
        log.info("Average imt - '{}'", massIndex);
        return massIndex;
    }


    public double massIndexCalculation(List<Person> adultPersons) {
        log.info("Рассчет средннго индекса массы тела");
        double result;
        double totalImt = 0.0;
        long countOfPerson = adultPersons.size();

        for (Person person : adultPersons) {
            double heightInMeters = person.getHeight() / 100d;
            double imt = person.getWeight() / (Double) (heightInMeters * heightInMeters);
            totalImt += imt;
        }

        result = totalImt / countOfPerson;

        log.info("Получено значение - '{}'", result);
        return result;
    }
}
