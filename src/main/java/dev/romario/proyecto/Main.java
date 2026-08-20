package dev.romario.proyecto;

import dev.romario.proyecto.controllers.BookController;
import dev.romario.proyecto.controllers.LoanController;
import dev.romario.proyecto.controllers.PartnerController;
import dev.romario.proyecto.models.Book;
import dev.romario.proyecto.models.Loan;
import dev.romario.proyecto.models.Partner;
import dev.romario.proyecto.repositories.Repositories;
import dev.romario.proyecto.repositories.RepositoryMemory;
import dev.romario.proyecto.services.BookService;
import dev.romario.proyecto.services.LoanService;
import dev.romario.proyecto.services.PartnerService;
import dev.romario.proyecto.views.BookMenu;
import dev.romario.proyecto.views.LoanMenu;
import dev.romario.proyecto.views.PartnerMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AppContext appContext = new AppContext(scanner);

        while (true){
            System.out.println("----------- MENU LIBRERIA ---------");
            System.out.println("1.- Gestion de libros");
            System.out.println("2.- Gestion de socios");
            System.out.println("3.- Gestion de prestamos");
            System.out.println("4.- Salir");

            System.out.print("Ingresa una opcion: ");
            String option = scanner.nextLine();

            switch (option){
                case "1" -> appContext.getBookMenu().show();
                case "2" -> appContext.getPartnerMenu().show();
                case "3" -> appContext.getLoanMenu().show();
                case "4" -> {
                    return;
                }
                default -> System.out.println("Opcion no valida");
            }
        }
    }
}
