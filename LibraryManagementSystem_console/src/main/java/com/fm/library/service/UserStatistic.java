package com.fm.library.service;

import org.springframework.stereotype.Component;


public class UserStatistic {

    String name;
    int totalPages;
    int totalReadBooks;
    String registrationDate;
    int totalBoughtBooks;


    public UserStatistic(String name, int totalPages, int totalReadBooks, String registrationDate, int totalBoughtBooks) {
        this.name = name;
        this.totalPages = totalPages;
        this.totalReadBooks = totalReadBooks;
        this.registrationDate = registrationDate;
        this.totalBoughtBooks = totalBoughtBooks;
    }

    public static UserStatisticBuilder builder() {
        return new UserStatisticBuilder();
    }


    public static class UserStatisticBuilder {

        private String name;
        private int totalPages;
        private int totalReadBooks;
        private String registrationDate;
        private int totalBoughtBooks;

        UserStatisticBuilder() {
        }

        public UserStatisticBuilder name(String name) {
            this.name = name;
            return UserStatisticBuilder.this;
        }

        public UserStatisticBuilder totalPages(int totalPages) {
            this.totalPages = totalPages;
            return UserStatisticBuilder.this;
        }

        public UserStatisticBuilder totalReadBooks(int totalReadBooks) {
            this.totalReadBooks = totalReadBooks;
            return UserStatisticBuilder.this;
        }

        public UserStatisticBuilder registrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
            return UserStatisticBuilder.this;
        }

        public UserStatisticBuilder totalBoughtBooks(int totalBoughtBooks) {
            this.totalBoughtBooks = totalBoughtBooks;
            return UserStatisticBuilder.this;
        }

        public UserStatistic build() {
            return new UserStatistic(this.name, this.totalPages, this.totalReadBooks, this.registrationDate, this.totalBoughtBooks);
        }
    }

    public String getName() {
        return name;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalReadBooks() {
        return totalReadBooks;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public int getTotalBoughtBooks() {
        return totalBoughtBooks;
    }



}
