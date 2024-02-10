package com.fm.library.service;

import java.util.List;

public class Registration {

    public boolean isUsernameAlreadyExists(String thisUserName) {
        User user = new User();
        List<User> users = user.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (thisUserName.equals(users.get(i).getUserName())) {
                System.out.println("Користувача з таким іменем вже зареєстровано.");
                System.out.println("Спробуйте інше ім'я.");
                return true;
            }
        }
        return false;
    }
}
