package dev.romario.proyecto.views;

import dev.romario.proyecto.controllers.LoanController;

import java.util.Scanner;

public class LoanMenu {
    private final LoanController loanController;
    private final Scanner scanner;

    public LoanMenu(LoanController loanController, Scanner scanner) {
        this.loanController = loanController;
        this.scanner = scanner;
    }

    public void show(){
        while (true) {
            System.out.println("------------ Gestion de prestamos ---------");
            System.out.println("1. Registar un prestamo");
            System.out.println("2. Registrar una devolucion");
            System.out.println("3. Buscar pretamos por socio");
            System.out.println("4. Mostrar todos los prestamos activos vencidos");
            System.out.println("5. Salir");

            System.out.print("Ingresa una opcion: ");
            String option = this.scanner.nextLine();

            switch (option) {
                case "1" -> registerLoan();
                case "2" -> registerReturn();
                case "3" -> findActiveLoansByPartner();
                case "4" -> listOverdueLoans();
                case "5" -> {
                    return;
                }
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    private void registerLoan(){
        System.out.print("Ingrese el Id del libro: ");
        String bookId = this.scanner.nextLine();
        System.out.print("Ingrese el Id del asociado: ");
        String partnerId = this.scanner.nextLine();
        this.loanController.registerLoan(bookId, partnerId);
    }
    private void registerReturn(){
        System.out.print("Ingrese el Id del prestamo: ");
        String loanId = this.scanner.nextLine();
        this.loanController.registerReturn(loanId);
    }
    private void findActiveLoansByPartner(){
        System.out.print("Ingrese el Id del asociado: ");
        String partnerId = this.scanner.nextLine();
        this.loanController.findActiveLoansByPartner(partnerId);
    }
    private void listOverdueLoans(){
        this.loanController.listOverdueLoans();
    }
}
