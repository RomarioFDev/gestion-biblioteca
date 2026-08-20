package dev.romario.proyecto.services;

import dev.romario.proyecto.exceptions.BookNotFoundException;
import dev.romario.proyecto.exceptions.DuplicateBookException;
import dev.romario.proyecto.models.Book;
import dev.romario.proyecto.repositories.Repositories;

import java.util.List;

public class BookService {
    private final Repositories<Book> bookRepository;

    public BookService(Repositories<Book> bookRepositories) {
        this.bookRepository = bookRepositories;
    }

     public void create(Book book){
        validationData(book);

        if (existsById(book.getId())){
            throw new DuplicateBookException("El libro ya existe en la base de datos");
        }
        this.bookRepository.create(book);
     }

     public List<Book> findAll(){
        return this.bookRepository.findAll();
     }

     public Book findById(String id){
        requiredNotBlank(id, "El id no puede estar vacio");
        return this.bookRepository.findById(id);
     }

     public void update(Book book){
        validationData(book);
         if (!existsById(book.getId())){
             throw new DuplicateBookException("El libro no existe en la base de datos");
         }
        this.bookRepository.update(book);
     }

     public void delete(String id){
        if (!existsById(id)){
            throw new BookNotFoundException("El libro no existe en la base de datos");
        }
        this.bookRepository.deleteById(id);
     }

     public List<Book> findByAuthor(String author){
        requiredNotBlank(author, "El autor no puede estar vacio");
        List<Book> booksByAuthor = this.bookRepository.findAll().stream().filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase())).toList();
        if (booksByAuthor.isEmpty()){
            throw new BookNotFoundException("No hay libros de ese autor");
        }
        return booksByAuthor;
     }
     public List<Book> findByTitle(String title){
        requiredNotBlank(title, "El titulo no puede estar vacio");
        List<Book> booksByTitle = this.bookRepository.findAll().stream().filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
        if (booksByTitle.isEmpty()){
            throw new BookNotFoundException("No hay libros con ese titulo");
        }
        return booksByTitle;
     }

     private void validationData(Book book){
         if (book == null){
             throw new BookNotFoundException("El libro no puede ser nulo");
         }
         requiredNotBlank(book.getAuthor(), "El autor no puede estar vacio");
         requiredNotBlank(book.getTitle(), "El titulo no puede estar vacio");

         if (book.getNumberOfCopies() < 0){
             throw new IllegalArgumentException("El numero de copias no pueden ser negativas o debe de ser mayor a cero");
         }
     }
     private void requiredNotBlank(String valor, String message){
        if (valor == null || valor.isBlank()){
            throw new IllegalArgumentException(message);
        }
    }

    private boolean existsById(String id){
        return this.bookRepository.findAll().stream().anyMatch(l -> l.getId().equals(id));
    }
}
