package ru.liga.intership.badcode.db;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import ru.liga.intership.badcode.BadcodeApplication;

public class DBConnectionTest {

    private DBConnection dbConnection = DBConnection.getInstance();

    @BeforeClass
    public static void beforeTest() {
        SpringApplication.run(BadcodeApplication.class);
    }

    @After
    public void after() {
        dbConnection.close();
    }

    @Test
    public void createPositiveTestCreate1() {
        Assertions.assertThat(DBConnection.getInstance().create())
                .as("Подключение не создано!")
                .isTrue();
    }

    @Test
    public void createPositiveTestCreate2() {
        Assertions.assertThat(DBConnection.getInstance().create("jdbc:hsqldb:mem:test", "sa", ""))
                .as("Подключение не создано!")
                .isTrue();
    }


    @Test
    public void createNegativeTest1() {
        Assertions.assertThat(DBConnection.getInstance().create("jdbc:hsqldb:mem:test", "ERROR", ""))
                .as("Подключение было создано!")
                .isFalse();
    }

    @Test
    public void createNegativeTest2() {
        Assertions.assertThat(DBConnection.getInstance().create("jdbc:hsqldb:mem:test", "", ""))
                .as("Подключение было создано!")
                .isFalse();
    }

    @Test
    public void createNegativeTest3() {
        Assertions.assertThat(DBConnection.getInstance().create("jdbc:hsqldb:mem:test", "sa", "ERROR"))
                .as("Подключение было создано!")
                .isFalse();
    }

    @Test
    public void createNegativeTest4() {
        Assertions.assertThat(DBConnection.getInstance().create("", "sa", ""))
                .as("Подключение было создано!")
                .isFalse();
    }

    @Test
    public void executeTest() {
        dbConnection.create();

        Assertions.assertThat(dbConnection.executeQuery("SELECT * FROM person"))
                .as("Значения не были переданы!")
                .isNotNull();
    }

    @Test
    public void closeTest() {
        dbConnection.create();

        Assertions.assertThat(dbConnection.close())
                .as("Подключение не было закрыто!")
                .isTrue();
    }
}
