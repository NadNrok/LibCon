package com.fm.library.manager;

import com.fm.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class MenuManager {

    @Autowired
    private BookService bookService;
    @Autowired
    private User user;
    @Autowired
    private Printer printer;


    public void startMenu() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|======     Вітаю у головному меню Бібліотеки      ========|");
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|   Виберіть необхідну дію:                                |");
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|   1 - показати перелік книг.                             |");
        System.out.println("|   2 - показати всіх авторів.                             |");
        System.out.println("|   7 - запропонувати додати книгу у асортимент.           |");
        System.out.println("|   8 - показати перелік запропонованих книг.              |");
        System.out.println("|  10 - показати статистику користувача.                   |");
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|   0 - повернутись назад.  |  99 - вихід з програми.      |");
        System.out.println("+----------------------------------------------------------+");
        System.out.print("|   Ваш вибір: ");

        int mainChoice;

        Map<Integer, Command> commands = new HashMap<>();
        commands.put(1, () -> bookService.showAllBooks());
        commands.put(2, () -> bookService.showAllAuthors());
        commands.put(7, () -> bookService.recommendBook());
        commands.put(8, () -> bookService.showRecommendedBooks());
        commands.put(10, () -> user.showUserStatistic());
        mainChoice = scanner.nextInt();
        if (mainChoice == 1 || mainChoice == 2 || mainChoice == 7 || mainChoice == 8|| mainChoice ==10){
            commands.get(mainChoice).run();
        } else {
            printer.printWrongCommand();
            startMenu();
        }
        if (mainChoice == 99) {
            return;
        }
    }
}

