package ru.liga.intership.badcode.db;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import ru.liga.intership.badcode.BadcodeApplication;

public class DBConnectionTest {

    @BeforeClass
    public static void beforeTest() {
        SpringApplication.run(BadcodeApplication.class);
    }

    @Test
    public void createPositiveTest() {
        Assertions.assertThat(new DBConnection().create("hsqldb"))
                .as("Подключение не создано!")
                .isTrue();
        Assertions.assertThat(new DBConnection().create("jdbc:hsqldb:mem:test", "sa", ""))
                .as("Подключение не создано!")
                .isTrue();
    }

    @Test
    public void createNegativeTest() {
        Assertions.assertThat(new DBConnection().create("MySQL"))
                .as("Подключение было создано!")
                .isFalse();
        Assertions.assertThat(new DBConnection().create("jdbc:hsqldb:mem:test", "ERROR", ""))
                .as("Подключение было создано!")
                .isFalse();
        Assertions.assertThat(new DBConnection().create("jdbc:hsqldb:mem:test", "", ""))
                .as("Подключение было создано!")
                .isFalse();
        Assertions.assertThat(new DBConnection().create("jdbc:hsqldb:mem:test", "sa", "ERROR"))
                .as("Подключение было создано!")
                .isFalse();
        Assertions.assertThat(new DBConnection().create("", "sa", ""))
                .as("Подключение было создано!")
                .isFalse();
    }

    @Test
    public void executeTest() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.create("hsqldb");

        Assertions.assertThat(dbConnection.executeQuery("SELECT * FROM person"))
                .as("Значения не были переданы!")
                .isNotNull();
    }

    @Test
    public void closeTest() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.create("hsqldb");

        Assertions.assertThat(dbConnection.close())
                .as("Подключение не было закрыто!")
                .isTrue();
    }
}
