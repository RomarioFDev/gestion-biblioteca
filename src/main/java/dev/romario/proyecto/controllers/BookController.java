package dev.romario.proyecto.controllers;

import dev.romario.proyecto.exceptions.BookNotFoundException;
import dev.romario.proyecto.exceptions.DuplicateBookException;
import dev.romario.proyecto.models.Book;
import dev.romario.proyecto.services.BookService;

import java.util.List;

public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void create(Book book){
        try {
            this.bookService.create(book);
            System.out.println("Libro creado exitosamente");
        } catch (IllegalArgumentException | BookNotFoundException e){
            System.out.println("Error al crear un nuevo libro: " + e.getMessage());
        }
    }

    public void findAll(){
        List<Book> books = this.bookService.findAll();
        if (books.isEmpty()){
            System.out.println("No hay libros registrados");
            return;
        }
        books.forEach(b -> System.out.println(b.getId() + " - " + b.getTitle() + " (" + b.getAuthor() + " Copias:" + b.getNumberOfCopies() +")"));
    }

    public void findById(String id){
        try {
            Book book = this.bookService.findById(id);
            System.out.println(book);
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findByAuthor(String author){
        try {
            List<Book> booksAuthor = this.bookService.findByAuthor(author);
            booksAuthor.forEach(b -> System.out.println("(" + b.getAuthor() + " " + b.getTitle() + " " + b.getNumberOfCopies()));
        } catch (IllegalArgumentException | BookNotFoundException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
    public void findByTitle(String title){
        try {
            List<Book> booksAuthor = this.bookService.findByTitle(title);
            booksAuthor.forEach(b -> System.out.println("(" + b.getTitle() + " " + b.getAuthor() + " " + b.getNumberOfCopies()));
        } catch (IllegalArgumentException | BookNotFoundException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public void update(Book book){
        try {
            this.bookService.update(book);
            System.out.println("El libro fue actualizado correctamente");
        } catch (IllegalArgumentException | DuplicateBookException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id){
        try {
            this.bookService.delete(id);
            System.out.println("Libro eliminado correctamente");
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
