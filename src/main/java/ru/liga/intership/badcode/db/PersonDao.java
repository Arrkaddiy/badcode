package ru.liga.intership.badcode.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDao implements IPersonDao {

    private static final Logger log = LoggerFactory.getLogger(PersonDao.class);

    private DBConnection dbConnection = DBConnection.getInstance();

    @Override
    public List<Person> getPersonsMenOverEighteenOld() {
        log.info("Получение всех пользователей мужского пола старше 18 лет из БД");
        List<Person> adultPersons = new ArrayList<>();
        Optional<ResultSet> resultSet = dbConnection.executeQuery("SELECT * FROM person WHERE sex = 'male' AND age > 18");
        if (resultSet.isPresent()) {
            adultPersons = mapResultSet(resultSet.get());
        }

        log.info("Получено значений Person - '{}'шт.", adultPersons.size());
        return adultPersons;
    }

    private List<Person> mapResultSet(ResultSet resultSet) {
        log.debug("Обработка данных из запроса");
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
}
