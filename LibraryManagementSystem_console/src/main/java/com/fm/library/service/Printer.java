package com.fm.library.service;

import com.fm.library.manager.MenuManager;
import com.fm.library.model.Book;
import com.fm.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Printer {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MenuManager menuManager;

    private static String ID = "|  id  |";
    private static String ISBN = "     Isbn     |";
    private static String NAME = "              Name               |";
    private static String AUTHOR = "            Author           |";
    private static String PAGES = " Pages |";
    private static String PRICE = " Price |";
    private static String RELEASE_YEAR = " Release Year |";



    public static void printBooksHeader() {


        System.out.println("+------------------------------------------------------------------------" +
                "-------+");
        System.out.println("|==============               Перелік доступних книжок         " +
                "=========+=======|");
        System.out.println("+------------------------------------------------------------------------" +
                "-------+");
        System.out.printf("%8s", ID);
        System.out.printf("%35s", NAME);
        System.out.printf("%30s", AUTHOR);
        System.out.printf("%8s", PRICE);
        System.out.println("");
        System.out.println("+------+----------------------------------+-----------------------------+" +
                "-------+");

    }

    public static void printBookHeader() {
        System.out.println("+------+--------------+----------------------------------+-----------------------------+" +
                "-------+--------------+-------+");
        System.out.println("|======+==============|                     Інформація про обрану книгу                |" +
                "=======+==============+=======|");
        System.out.println("+------+--------------+----------------------------------+-----------------------------+" +
                "-------+--------------+-------+");
        System.out.printf("%8s", ID);
        System.out.printf("%14s", ISBN);
        System.out.printf("%35s", NAME);
        System.out.printf("%30s", AUTHOR);
        System.out.printf("%8s", PAGES);
        System.out.printf("%15s", RELEASE_YEAR);
        System.out.printf("%8s", PRICE);
        System.out.println("");
        System.out.println("+------+--------------+----------------------------------+-----------------------------+" +
                "-------+--------------+-------+");

    }


    public static void printAuthorsHeader() {
        System.out.println("+------------------------------------+");
        System.out.println("|===  Перелік доступних авторів  ====|");
        System.out.println("+------------------------------------+");
        System.out.printf("%8s", ID);
        System.out.printf("%30s\n", AUTHOR);
        System.out.println("+------+-----------------------------+");
    }

    public static void printRecommendedHeader() {
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|===============      Перелік запропонованих книг     ==================|");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.printf("%8s", ID);
        System.out.printf("%35s", NAME);
        System.out.printf("%30s\n", AUTHOR);
        System.out.println("+------+----------------------------------+-----------------------------+");
    }

    public static void printBooks(List<Book> bookList) {
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            System.out.printf("|" + "%7s", +book.getId() + "   |");
            System.out.printf("%-33s |", book.getName());
            System.out.printf(" %-28s|", book.getAuthor());
            System.out.printf("%8s\n", book.getPrice() + "   |");
            System.out.println("+------+----------------------------------+-----------------------------+" +
                    "-------+");
        }
    }

    public static void printAuthors(List<Book> authorList) {
        for (int i = 0; i < authorList.size(); i++) {
            Book author = authorList.get(i);
            System.out.printf("|" + "%7s", +author.getId() + "   |");
            System.out.printf(" %-28s|\n", author.getAuthor());
            System.out.println("+------+-----------------------------+");
        }
    }

    public static void printRecommendedBooks(List<Book> recommendedBooksList) {
        for (int i = 0; i < recommendedBooksList.size(); i++) {
            Book book = recommendedBooksList.get(i);
            System.out.printf("|" + "%7s", +book.getId() + "   |");
            System.out.printf(" %-33s|", book.getName());
            System.out.printf(" %-28s|\n", book.getAuthor());
            System.out.println("+------+----------------------------------+-----------------------------+");
        }
    }

    public static void printThisBook(Book book) {
        System.out.printf("|" + "%7s", +book.getId() + "   |");
        System.out.printf("%16s", book.getIsbn() + " | ");
        System.out.printf("%-33s|", book.getName());
        System.out.printf(" %-28s|", book.getAuthor());
        System.out.printf("%8s", book.getPages() + "  |");
        System.out.printf("%15s", book.getReleaseYear() + "     |");
        System.out.printf("%8s\n", book.getPrice() + "   |");
        System.out.println("+------+--------------+----------------------------------+-----------------------------+" +
                "-------+--------------+-------+");
    }

    public static void printThisAuthorBooks(List<Book> books) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("|" + "%7s", +book.getId() + "   |");
            System.out.printf("%-33s |", book.getName());
            System.out.printf(" %-28s|", book.getAuthor());
            System.out.printf("%8s\n", book.getPrice() + "   |");
            System.out.println("+------+----------------------------------+-----------------------------+" +
                    "-------+");
        }
    }

    public void printUserStatisticHeader(String username) {
        System.out.println();
        System.out.println("+----------------------+-----------------------------------------------------------------------------------------------------+");
        System.out.print("|======================+");
        System.out.printf("%78s", "Welcome to " + username + "`s Statistic                        ");
        System.out.println("+======================|");
        System.out.println("+----------------------+--------------------------------+----------------------+----------------------+----------------------+");
        System.out.println("|       Username       |        Registration date       |   Total read pages   |   Total read books   |  Total bought books  |");
        System.out.println("+----------------------+--------------------------------+----------------------+----------------------+----------------------+");

    }

    public  void printUserStatistic(String userName) {
        UserStatistic userStatistic = userRepository.getUsersStatistic(userName);
                System.out.printf("|  %-20s", userStatistic.getName());
                System.out.printf("|  %-30s", userStatistic.getRegistrationDate());
                System.out.printf("|  %-20s", userStatistic.getTotalPages());
                System.out.printf("|  %-20s", userStatistic.getTotalReadBooks());
                System.out.printf("|  %-20s", userStatistic.getTotalBoughtBooks()) ;
                System.out.println("|");
                System.out.println("+----------------------+--------------------------------+----------------------+----------------------+----------------------+");
    }

    public int printFooter() {
        System.out.println("+-----------------------------------------------------+ ");
        System.out.println("|  0 - повренутись у попереднє меню.                  |");
        System.out.println("| 88 - повернутись у головне меню.                    |");
        System.out.println("| 99 - вихід з програми.                              |");
        System.out.println("+-----------------------------------------------------+ ");
        System.out.print("| Ваш вибір: ");
        Scanner scanner = new Scanner(System.in);
        int choice2 = scanner.nextInt();
        return choice2;
    }

    public void printWrongCommand() {
        System.out.println();
        System.out.println("+----------------------------------------------------------+");
        System.out.println("| Обрано невірну команду, повторіть, будь ласка, спробу.   |");
        System.out.println("+----------------------------------------------------------+");
    }
}
