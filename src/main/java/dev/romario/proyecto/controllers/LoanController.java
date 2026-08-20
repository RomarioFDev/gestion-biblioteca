package dev.romario.proyecto.controllers;

import dev.romario.proyecto.exceptions.*;
import dev.romario.proyecto.models.Loan;
import dev.romario.proyecto.services.LoanService;

import java.util.List;

public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public void registerLoan(String bookId, String partnerId){
        try {
            Loan loan = this.loanService.registerLoan(bookId, partnerId);
            System.out.println("El prestamo fue registrado exitosamente el ID: " + loan.getId());
        } catch (IllegalArgumentException | BookNotFoundException | PartnerNotFoundException | NoAvailableCopiesException |
                 LoanLimitExceededException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void registerReturn(String loanId){
        try {
            this.loanService.registerReturn(loanId);
            System.out.println("Devolucion registrada con exito");
    } catch (IllegalArgumentException | LoanNotFoundException | LoanAlreadyReturnedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findActiveLoansByPartner(String partnerId){
        try {
            List<Loan> loans = this.loanService.findActiveLoansByPartner(partnerId);
            if (printIfEmpty(loans, "No hay prestamos activos del asociado")){
                return;
            }
            loans.forEach(l -> System.out.println("ID: " + l.getId() + "\nID Libro: " + l.getBookId() + "\nFecha prestamo: " + l.getLoanDate() + "\nFecha devolucion: " + l.getDueDate() + "\nEstatus: " + l.getStatusLoan()));
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listOverdueLoans(){
        List<Loan> loans = this.loanService.listOverdueLoans();
        if (printIfEmpty(loans, "No hay prestamos activos atrasados")){
            return;
        }
        loans.forEach(l -> System.out.println("ID: " + l.getId() + "\nID Socio: " + l.getPartnerId() + "\nID Libro: " + l.getBookId() + "\nFecha prestamo: " + l.getLoanDate() + "\nFecha devolucion: " + l.getDueDate() + "\nEstatus: " + l.getStatusLoan()));
    }

    private <T> boolean printIfEmpty(List<T> list, String message){
        if (list.isEmpty()){
            System.out.println(message);
        }
        return true;
    }
}
