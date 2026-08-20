package dev.romario.proyecto.models;

import dev.romario.proyecto.repositories.Identifiable;

public class Book implements Identifiable {
    private String id;
    private String author;
    private String title;
    private int numberOfCopies;

    public Book(String id, String author, String title, int numberOfCopies) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.numberOfCopies = numberOfCopies;
    }

    @Override
    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", numberOfCopies=" + numberOfCopies +
                '}';
    }
}
