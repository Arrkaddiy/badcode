package ru.liga.intership.badcode.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.db.DBConnection;
import ru.liga.intership.badcode.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private static final String DATA_BASE = "hsqldb";
    private static final String SQL = "SELECT * FROM person WHERE sex = 'male' AND age > 18";


    /**
     * Возвращает средний индекс массы тела всех лиц мужского пола старше 18 лет
     */
    public void getAdultMaleUsersAverageBMI() {
        log.debug("Рассчет среднего индекса массы тела всех лиц мужского пола старше 18 лет");
        double massIndex = 0.0;
        Optional<ResultSet> resultSet = getDataFromDB();

        if (resultSet.isPresent()) {
            List<Person> adultPersons = parsResultSet(resultSet.get());
            massIndex = massIndexCalculation(adultPersons);
        }

        log.debug("Average imt - '{}'", massIndex);
    }

    public Optional<ResultSet> getDataFromDB() {
        log.debug("Получение данных из БД");
        Optional<ResultSet> resultSet = Optional.empty();
        DBConnection dbConnection = new DBConnection();

        if (dbConnection.create(DATA_BASE)) {
            resultSet = dbConnection.executeQuery(SQL);
            dbConnection.close();
        }

        log.debug("Значения получены - '{}'", resultSet.isPresent());
        return resultSet;
    }

    public List<Person> parsResultSet(ResultSet resultSet) {
        log.debug("Обработка данных из БД");
        List<Person> adultPersons = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong(Person.DBField.ID));
                person.setSex(resultSet.getString(Person.DBField.SEX));
                person.setName(resultSet.getString(Person.DBField.NAME));
                person.setAge(resultSet.getLong(Person.DBField.AGE));
                person.setWeight(resultSet.getLong(Person.DBField.WEIGHT));
                person.setHeight(resultSet.getLong(Person.DBField.HEIGHT));
                adultPersons.add(person);
            }

        } catch (SQLException sqlE) {
            log.error("Ошибка обработки данных - '{}'", sqlE.getMessage());
        }

        log.debug("Получено значений Person - '{}'шт.", adultPersons.size());
        return adultPersons;
    }

    public double massIndexCalculation(List<Person> adultPersons) {
        log.debug("Рассчет средннго индекса массы тела");
        double result;
        double totalImt = 0.0;
        long countOfPerson = adultPersons.size();

        for (Person person : adultPersons) {
            double heightInMeters = person.getHeight() / 100d;
            double imt = person.getWeight() / (Double) (heightInMeters * heightInMeters);
            totalImt += imt;
        }

        result = totalImt / countOfPerson;

        log.debug("Получено значение - '{}'", result);
        return result;
    }
}
