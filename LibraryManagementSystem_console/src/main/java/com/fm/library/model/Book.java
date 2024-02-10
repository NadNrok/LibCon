package com.fm.library.model;

public class Book {
    int id;
    long isbn;
    String author;
    String name;
    int pages;
    int price;
    int releaseYear;

    public Book(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Book(int id, String author, String name, int price) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public long getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public int getPages() {
        return pages;
    }

    public int getPrice() {
        return price;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Book(int id, long isbn, String author, String name, int pages, int price, int releaseYear) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.name = name;
        this.pages = pages;
        this.price = price;
        this.releaseYear = releaseYear;
    }

    public Book(int id, String author) {
        this.id = id;
        this.author = author;
    }
}
