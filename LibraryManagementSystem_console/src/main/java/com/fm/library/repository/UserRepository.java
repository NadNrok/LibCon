package com.fm.library.repository;

import com.fm.library.manager.ConnectionManager;
import com.fm.library.service.User;
import com.fm.library.service.UserStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    public static final String SELECT_FROM_USERS_QUERY = "select * from users";
    public static final String SELECT_FROM_USER_STATISTIC_QUERY = "SELECT * from user_statistic";

    @Autowired
    private ConnectionManager connectionManager;

    public List<User> getAllUsers() {
        final ResultSet resultSet = connectionManager.executeQuery(SELECT_FROM_USERS_QUERY);
        final List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String name = resultSet.getString("username");
                String userPassword = resultSet.getString("password");
                users.add(new User(name, userPassword));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    public UserStatistic getUsersStatistic(String userName) {
        final ResultSet resultSet = connectionManager.executeQuery("SELECT * from user_statistic WHERE username = '" + userName +"' ");
        UserStatistic userStatistic = null;
        try {
            while (resultSet.next()) {
                userStatistic = UserStatistic.builder()
                        .name(resultSet.getString("username"))
                        .totalPages(resultSet.getInt("total_pages"))
                        .totalReadBooks(resultSet.getInt("total_read_books"))
                        .registrationDate(resultSet.getString("registration_date"))
                        .totalBoughtBooks(resultSet.getInt("total_bought_books"))
                        .build();
                return userStatistic;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            //todo implement me (catch)
        }
        return userStatistic;
    }

}
