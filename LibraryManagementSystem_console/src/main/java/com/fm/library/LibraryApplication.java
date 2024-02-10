package com.fm.library;

import com.fm.library.manager.MenuManager;
import com.fm.library.service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

    @Autowired
    private MenuManager menuManager;


    //fixme remove me
    private User user = new User();

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public Scanner scanner() {
        //todo investigate this posibility of spring;
        return new Scanner(System.in);
    }

    @Override
    public void run(final String... args) {
        //todo move logic from user class to UserManager class
        boolean logged = user.registerOrSignIn();
        if (logged) {
            menuManager.startMenu();
        }
    }
}
