package com.fm.library.service;

import com.fm.library.manager.ConnectionManager;
import com.fm.library.manager.MenuManager;
import com.fm.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;


@Service
public class BookService {
    Scanner scanner = new Scanner(System.in);
    int choice;
    @Autowired
    private MenuManager menuManager;
    @Autowired
    Printer printer;
    @Autowired
    User user;
    @Autowired
    ConnectionManager connectionManager;


    private List<Book> getAllBooks() {
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<Book> booksList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books, authors WHERE author_id = id_author  ORDER BY id");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                long isbn = resultSet.getLong("isbn");
                String author = resultSet.getString("author");
                int pages = resultSet.getInt("pages");
                int price = resultSet.getInt("price");
                int releaseYear = resultSet.getInt("release_year");
                booksList.add(new Book(id, isbn, author, name, pages, price, releaseYear));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //TODO опрацювати (щось зробити, якщо нема connection до бази даних
        }
        return booksList;
    }


    private List<Book> getAuthors() {
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<Book> authorsList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_author, author FROM authors");
            while (resultSet.next()) {
                int id = resultSet.getInt("id_author");
                String author = resultSet.getString("author");
                authorsList.add(new Book(id, author));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return authorsList;
    }

    public void showAllBooks() {
        Printer.printBooksHeader();
        Printer.printBooks(getAllBooks());
        System.out.println("+-----------------------------------------------------+ ");
        System.out.println("|  (введіть id) - щоб обрати книгу.                   |");
        choice = printer.printFooter();
        if (choice == 0) {
            menuManager.startMenu();
        }
        if (choice == 88) {
            menuManager.startMenu();
        } else {
            selectBook(choice);
        }

    }

    public void showAllAuthors() {
        Printer.printAuthorsHeader();
        Printer.printAuthors(getAuthors());
        System.out.println("+-----------------------------------------------------+ ");
        System.out.println("| (введіть id) - щоб обрати автора.                   |");
        choice = printer.printFooter();
        if (choice >= 1 && choice < 80) {
            showAuthorBooks(choice);
            System.out.println("+-----------------------------------------------------+ ");
            System.out.println("| (введіть id) - щоб обрати книгу.                    |");
            choice = printer.printFooter();
            if (choice >= 1 && choice < 80) {
                selectBook(choice);
            }
        }
        if (choice == 88 || choice == 0) {
            menuManager.startMenu();
        }
    }

    public void recommendBook() {

        String author = recommendAuthor();
        String name = recommendName();
        BookService.addRecommendedBook(name, author);
        System.out.println("+-----------------------------------------------------+ ");
        System.out.println("| Дякуємо за рекомендацію, ми скоро її розглянемо.    |");
        System.out.printf("| %-52s", "Ваша рекомендація: " + author + " - \"" + name + "\".");
        System.out.println("|");
        choice = printer.printFooter();

        if (choice == 88 || choice == 0) {
            menuManager.startMenu();
            return;
        }
    }

    public String recommendAuthor() {
        Scanner author = new Scanner(System.in);
        System.out.println("+-----------------------------------------------------+ ");
        System.out.println("| Введіть автора книги щоб зробити замовлення.        |");
        System.out.println("+-----------------------------------------------------+ ");
        System.out.println("|  0 - повренутись у попереднє меню.                  |");
        System.out.println("| 88 - повернутись у головне меню.                    |");
        System.out.println("| 99 - вихід з програми.                              |");
        System.out.println("+-----------------------------------------------------+ ");
        System.out.print("| Введіть автора або зробіть вибір: ");
        String authorRecommendation = author.nextLine();
        if (authorRecommendation.equals("88") || authorRecommendation.equals("0")) {
            menuManager.startMenu();
        } else {
            return authorRecommendation;
        }
        return authorRecommendation;
    }

    public String recommendName() {
        Scanner name = new Scanner(System.in);
        System.out.print("Введіть назву книги: ");
        String nameRecommendation = name.nextLine();
        return nameRecommendation;
    }

    private List<Book> getRecommendedBooks() {
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<Book> recommendedList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM recommended_books");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");

                recommendedList.add(new Book(id, name, author));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return recommendedList;
    }

    public static List<Book> addRecommendedBook(String name, String author) {
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<Book> recommendedList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            String sql = "INSERT INTO recommended_books (name, author) Values (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return recommendedList;
    }

    public void showRecommendedBooks() {
        Printer.printRecommendedHeader();
        Printer.printRecommendedBooks(getRecommendedBooks());
        choice = printer.printFooter();
        if (choice == 0 || choice == 88) {
            menuManager.startMenu();
            return;
        }
    }

    public void selectBook(int idBook) {
        Printer.printBookHeader();
        Printer.printThisBook(getThisBook(idBook));
        System.out.println("+-----------------------------------------------------+");
        System.out.println("| 5 - купити книгу.        | 6 - орендувати книгу     |");
        choice = printer.printFooter();
        if (choice == 0) {
            showAllBooks();
        }
        if (choice == 88) {
            menuManager.startMenu();
        }
        if (choice == 5) {
            buyBook(idBook);
        }
        if (choice == 6) {
            rentBook(idBook);
        }
    }

    private void rentBook(int idBook) {
        System.out.println("+-----------------------------------------------------+");
        System.out.println("| Авторизуйтесь повторно щоб орендувати книгу.        |");
        String userName = user.signIn2();
        countLeftBooks(idBook);
        countReadBooks(userName);
        moveBookInRent(userName, idBook);
        menuManager.startMenu();
    }

    private void moveBookInRent(String userName, int idBook) {
        addBookInRent(idBook, userName);
    }

    private void addBookInRent(int idBook, String userName) {
        final int getNumOfBooks = connectionManager.executeUpdate("INSERT INTO rent_books (book_id, book_name, author, user_name) VALUES (" +
                getThisBook(idBook).getId() + ", \'" + getThisBook(idBook).getName() + "\' , \'" + getThisBook(idBook).getAuthor() + "\' , \'" + userName + "\')");
        String sql = "INSERT INTO rent_books (book_id, book_name, author, user_name) Values (?, ?, ?, ?)";
    }

    private void countReadBooks(String userName) {
        int numOfBooks = 0;
        final ResultSet getNumOfBooks = connectionManager.executeQuery("SELECT total_read_books FROM user_statistic WHERE username = '" + userName + "'");
        try {
            while (getNumOfBooks.next()) {
                numOfBooks = getNumOfBooks.getInt("total_read_books") + 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        final int countBoughtBooks = connectionManager.executeUpdate("UPDATE user_statistic SET total_read_books =" + numOfBooks + "WHERE username = '" + userName + "'");
        try {
            while (getNumOfBooks.next()) {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void buyBook(int idBook) {
        System.out.println("+-----------------------------------------------------+");
        System.out.println("| Авторизуйтесь повторно щоб придбати книгу.          |");
        String userName = user.signIn2();
        countLeftBooks(idBook);
        countBoughtBooks(userName);
        countReadBooks(userName);
        menuManager.startMenu();
    }

    private void countBoughtBooks(String userName) {
        int numOfBooks = 0;
        final ResultSet getNumOfBooks = connectionManager.executeQuery("SELECT total_bought_books FROM user_statistic WHERE username = '" + userName + "'");
        try {
            while (getNumOfBooks.next()) {
                numOfBooks = getNumOfBooks.getInt("total_bought_books") + 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        final int countBoughtBooks = connectionManager.executeUpdate("UPDATE user_statistic SET total_bought_books =" + numOfBooks + "WHERE username = '" + userName + "'");
        try {
            while (getNumOfBooks.next()) {

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void countLeftBooks(int idBook) {
        int numOfBooks = 0;
        final ResultSet getNumOfBooks = connectionManager.executeQuery("SELECT num_of_books FROM books WHERE id = " + idBook);
        try {
            while (getNumOfBooks.next()) {
                numOfBooks = getNumOfBooks.getInt("num_of_books") - 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (numOfBooks == -1) {
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| На даний момент цієї книги нема у наявності.        |");
        } else {
            final int changeNumOfBooks = connectionManager.executeUpdate("UPDATE books SET num_of_books =" + numOfBooks + "WHERE id =" + idBook);
            try {
                while (getNumOfBooks.next()) {

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            System.out.println("+-----------------------------------------------------+");
            System.out.println("| Операція успішна!                                   |");
        }
    }

    public void showAuthorBooks(int authorId) {
        Printer.printBooksHeader();
        Printer.printThisAuthorBooks(getAuthorBooks(authorId));
    }

    private List<Book> getAuthorBooks(int authorId) {
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<Book> booksList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books, authors WHERE author_id = id_author  ORDER BY id");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int author_Id = resultSet.getInt("author_id");
                int id = resultSet.getInt("id");
                long isbn = resultSet.getLong("isbn");
                String author = resultSet.getString("author");
                int pages = resultSet.getInt("pages");
                int price = resultSet.getInt("price");
                int releaseYear = resultSet.getInt("release_year");
                if (authorId == author_Id)
                    booksList.add(new Book(id, isbn, author, name, pages, price, releaseYear));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return booksList;
    }

    private Book getThisBook(int idBook) {
        String connectionUrl = "jdbc:postgresql://localhost:5432/library_books";
        String userName = "postgres";
        String password = "07081998";
        List<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, isbn, author, name, pages, price, release_year FROM books, authors WHERE author_id = id_author ORDER BY id");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                long isbn = resultSet.getLong("isbn");
                String author = resultSet.getString("author");
                int pages = resultSet.getInt("pages");
                int price = resultSet.getInt("price");
                int releaseYear = resultSet.getInt("release_year");
                books.add(new Book(id, isbn, author, name, pages, price, releaseYear));
                for (int i = 0; i < books.size(); i++) {
                    if (i == idBook - 1) {
                        books.get(i);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books.get(idBook - 1);
    }
}
