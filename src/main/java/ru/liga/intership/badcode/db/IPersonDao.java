package ru.liga.intership.badcode.db;

import ru.liga.intership.badcode.domain.Person;

import java.util.List;

public interface IPersonDao {

    List<Person> getPersonsMenOverEighteenOld();
}
