package com.fm.library.manager;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;

@Component
public class ConnectionManager {

    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/library_books";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "07081998";

    private Connection connection;

    @PostConstruct
    public void init() throws SQLException {
        //todo remove throws and add try/catch with logic if first connection was not successful
        connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
    }

    public ResultSet executeQuery(final String sql) {
        try {
            final Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException exception) {
            //todo implement connection issues logic;
            exception.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(final String sql) {
        try {
            final Statement statement = connection.createStatement();
           return statement.executeUpdate(sql);
        } catch (SQLException exception) {
            //todo implement connection issues logic;
            exception.printStackTrace();
            return -1;
        }
    }
}
