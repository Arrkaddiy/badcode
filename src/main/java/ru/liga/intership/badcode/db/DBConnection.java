package ru.liga.intership.badcode.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.properties.PropertiesReader;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class DBConnection {

    private static final Logger log = LoggerFactory.getLogger(DBConnection.class);

    private Connection connection;
    private Statement statement;

    public boolean create(String db) {
        Properties properties = PropertiesReader.getProperties();
        return create(
                properties.getProperty(db + ".db.url"),
                properties.getProperty(db + ".db.login"),
                properties.getProperty(db + ".db.password")
        );
    }

    public boolean create(String url, String login, String password) {
        log.info("Подключение к базе - '{}', '{}'", url, login);
        boolean isConnect;


        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, login, password);
                statement = connection.createStatement();

            } else {

                if (!connection.isClosed()) {
                    log.info("Подключение уже создано!");
                } else {
                    close();
                    log.error("Ошибка подключения! Повторите попытку!");
                }
            }

            isConnect = !connection.isClosed() && !statement.isClosed();

        } catch (SQLException sqlE) {
            log.error("Ошибка подключения - '{}'", sqlE.getMessage());
            return false;
        }


        log.info("Успех подключения - '{}'", isConnect);
        return isConnect;
    }

    public Optional<ResultSet> executeQuery(String sql) {
        log.info("Выполняем запрос - '{}'", sql);
        Optional<ResultSet> resultSet = Optional.empty();

        try {
            if (connection != null && statement != null && !connection.isClosed() && !statement.isClosed()) {
                resultSet = Optional.of(statement.executeQuery(sql));
            } else {
                log.error("Отсутствует подключение к БД!");
            }

        } catch (SQLException sqlE) {
            log.error("Ошибка выполнения запроса - '{}'", sqlE.getMessage());
        }

        return resultSet;
    }

    public boolean close() {
        log.info("Закрытие подключения");
        try {

            if (statement != null && !statement.isClosed()) {
                statement.close();
                statement = null;
            }

            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }

        } catch (SQLException sqlE) {
            log.error("Ошибка отключения от БД - '{}'", sqlE.getMessage());
        }

        return statement == null && connection == null;
    }
}
