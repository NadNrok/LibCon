package com.fm.library.service;

import com.fm.library.manager.ConnectionManager;
import com.fm.library.manager.MenuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class User {
    @Autowired
    Printer printer;
    @Autowired
    MenuManager menuManager;

    int chose;
    private String userName;
    String registrationDate;
    int totalPages;
    int totalReadBooks;
    int totalBoughtBooks;

    public User(String userName, String registrationDate, int totalPages, int totalReadBooks, int totalBoughtBooks) {
        this.userName = userName;
        this.registrationDate = registrationDate;
        this.totalPages = totalPages;
        this.totalReadBooks = totalReadBooks;
        this.totalBoughtBooks = totalBoughtBooks;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    private String userPassword;

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User() {
    }

    public void showUserStatistic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+----------------------------------------------------------+");
        System.out.println();
        System.out.println("+-------------------------------------------------------------------------+");
        System.out.print("| Введіть логін користувача якого хочете побачити статистику: ");
        String userName = scanner.next();
        System.out.println("+-------------------------------------------------------------------------+");
        printer.printUserStatisticHeader(userName);
        printer.printUserStatistic(userName);
        chose = printer.printFooter();
        if (chose == 0 || chose == 88) {
            menuManager.startMenu();
        }
    }

    public boolean registerOrSignIn() {
        boolean isLogged = false;
        boolean done = false;
        do {
            System.out.println();
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| Вітаємо у бібліотеці, увійдіть або зареєструйтесь.  |");
            System.out.println("+-----------------------------------------------------+");
            System.out.println("|       1 - Увійти.        |  2 - Зареєструватись.    |");
            System.out.println("+-----------------------------------------------------+");
            System.out.print("| Ваш вибір: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            System.out.println("+-----------------------------------------------------+");
            if (choice == 1) {
                isLogged = signIn();
                done = true;
            }
            if (choice == 2) {
                isLogged = registration();
                done = true;
            }
            if (choice != 1 && choice != 2) {
                printer.printWrongCommand();
            }
        } while (!done);
        return isLogged;
    }


    public boolean signIn() {
        boolean isLogged = false;
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<User> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users ");
            while (resultSet.next()) {
                String name = resultSet.getString("username");
                String userPassword = resultSet.getString("password");
                users.add(new User(name, userPassword));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Scanner nameScanner = new Scanner(System.in);
        Scanner passwordScanner = new Scanner(System.in);
        System.out.print("| Логін: ");
        String nameInput = nameScanner.next();
        System.out.println("+-------------------------+");
        System.out.print("| Пароль: ");
        String passwordInput = passwordScanner.next();
        System.out.println("+-------------------------+");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserName().equals(nameInput) && user.getUserPassword().equals(passwordInput)) {
                isLogged = true;
                break;
            }
        }
        while (isLogged == false) {
            System.out.println("| Логін або пароль було введено невірно.              |");
            System.out.println("| Повторіть, будь ласка спробу.                       |");
            System.out.println("+-----------------------------------------------------+");
            isLogged = signIn();

        }
        return isLogged;
    }

    public String signIn2() {
        boolean isLogged = false;
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<User> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users ");
            while (resultSet.next()) {
                String name = resultSet.getString("username");
                String userPassword = resultSet.getString("password");
                users.add(new User(name, userPassword));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Scanner nameScanner = new Scanner(System.in);
        Scanner passwordScanner = new Scanner(System.in);
        System.out.print("| Логін: ");
        String nameInput = nameScanner.next();
        System.out.println("+-----------------------------------------------------+");
        System.out.print("| Пароль: ");
        String passwordInput = passwordScanner.next();
        System.out.println("+-----------------------------------------------------+");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserName().equals(nameInput) && user.getUserPassword().equals(passwordInput)) {
                isLogged = true;
                break;
            }
        }
        while (isLogged == false) {
            System.out.println("| Логін або пароль було введено невірно.              |");
            System.out.println("| Повторіть, будь ласка спробу.                       |");
            System.out.println("+-----------------------------------------------------+");
            signIn();

        }
        return nameInput;
    }

    public boolean registration() {
        boolean isLogged;
        Scanner scanner = new Scanner(System.in);
        System.out.println("| Реєстрація нового користувача.                      |");
        System.out.println("+-----------------------------------------------------+");
        System.out.print("| Введіть логін: ");
        String userNameInput = scanner.next();
        System.out.println("+-----------------------------------------------------+");
        System.out.print("| Введіть пароль: ");
        String userPasswordInput = scanner.next();
        System.out.println("+-----------------------------------------------------+");
        Registration registration = new Registration();
        java.util.Date date = new java.util.Date();

        boolean isUsernameAlreadyExists = registration.isUsernameAlreadyExists(userNameInput);

        if (isUsernameAlreadyExists) {
            //todo try remove recursive method call
            isLogged = registration();
        } else {
            createNewUser(userNameInput, userPasswordInput, date);
            isLogged = true;
        }
        return isLogged;
    }

    private void createNewUser(String userNameInput, String userPasswordInput, java.util.Date date) {
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        //todo move method to user repository
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            String sql = "INSERT INTO users (username, password) Values (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userNameInput);
            preparedStatement.setString(2, userPasswordInput);
            preparedStatement.execute();
            String sql2 = "INSERT INTO user_statistic (username,total_pages, total_read_books, registration_date, total_bought_books) Values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement2.setString(1, userNameInput);
            preparedStatement2.setInt(2, 0);
            preparedStatement2.setInt(3, 0);
            //todo check if it woks;
            preparedStatement2.setString(4, date.toString());
            preparedStatement2.setInt(5, 0);
            preparedStatement2.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getUsers() {

        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<User> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String name = resultSet.getString("username");
                String userPassword = resultSet.getString("password");
                users.add(new User(name, userPassword));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }
}
