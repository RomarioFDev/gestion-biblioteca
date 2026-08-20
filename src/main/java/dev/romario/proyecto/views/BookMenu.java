package dev.romario.proyecto.views;

import dev.romario.proyecto.controllers.BookController;
import dev.romario.proyecto.models.Book;

import java.util.Scanner;

public class BookMenu {
    private final BookController bookController;
    private final Scanner scanner;

    public BookMenu(BookController bookController, Scanner scanner) {
        this.bookController = bookController;
        this.scanner = scanner;
    }

    public void show(){
        while (true){
            System.out.println("------------ MENU DE LIBROS -----------");
            System.out.println("1. Agregar un libro");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar libro por id");
            System.out.println("4. Listar libros por autor");
            System.out.println("5. Listar libros por titulo");
            System.out.println("6. Actualizar libro");
            System.out.println("7. Eliminar libro");
            System.out.println("8. Salir");

            System.out.print("Ingrese una opcion: ");
            String option = this.scanner.nextLine();
            System.out.println();

            switch (option){
                case "1" -> create();
                case "2" -> this.bookController.findAll();
                case "3" -> findById();
                case "4" -> findByAuthor();
                case "5" -> findByTitle();
                case "6" -> update();
                case "7" -> delete();
                case "8" -> {
                    return;
                }
                default -> System.out.println("Opcion no disponible");
            }
        }
    }

    private void create(){
        System.out.print("Ingrese el ID: ");
        String id = this.scanner.nextLine();
        System.out.print("Ingrese el Autor: ");
        String author = this.scanner.nextLine();
        System.out.print("Ingrese el Titulo: ");
        String title = this.scanner.nextLine();
        System.out.print("Ingrese el numero de copias: ");
        int copies = Integer.parseInt(this.scanner.nextLine());

        this.bookController.create(new Book(id, author, title, copies));
    }

    private void findById(){
        System.out.print("Ingrese el ID: ");
        String id = this.scanner.nextLine();
        this.bookController.findById(id);
    }
    private void findByAuthor(){
        System.out.print("Ingrese el Autor: ");
        String author = this.scanner.nextLine();
        this.bookController.findByAuthor(author);
    }
    private void findByTitle(){
        System.out.print("Ingrese el Titulo: ");
        String title = this.scanner.nextLine();
        this.bookController.findByTitle(title);
    }
    private void update(){
        System.out.print("Ingrese el ID: ");
        String id = this.scanner.nextLine();
        System.out.print("Ingrese el Autor: ");
        String author = this.scanner.nextLine();
        System.out.print("Ingrese el Titulo: ");
        String title = this.scanner.nextLine();
        System.out.print("Ingrese el numero de copias: ");
        int copies = Integer.parseInt(this.scanner.nextLine());

        this.bookController.update(new Book(id, author, title, copies));
    }
    private void delete(){
        System.out.print("Ingrese el ID: ");
        String id = this.scanner.nextLine();

        this.bookController.delete(id);
    }
}
