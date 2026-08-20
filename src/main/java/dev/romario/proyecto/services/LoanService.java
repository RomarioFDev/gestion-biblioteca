package dev.romario.proyecto.services;

import dev.romario.proyecto.exceptions.*;
import dev.romario.proyecto.models.Book;
import dev.romario.proyecto.models.Loan;
import dev.romario.proyecto.models.Partner;
import dev.romario.proyecto.models.StatusLoan;
import dev.romario.proyecto.repositories.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class LoanService {
    private static final int MAX_ACTIVE_LOANS = 3;

    private final Repositories<Loan> loanRepositories;
    private final Repositories<Book> bookRepositories;
    private final Repositories<Partner> partnerRepositories;

    public LoanService(Repositories<Loan> loanRepositories, Repositories<Book> bookRepositories, Repositories<Partner> partnerRepositories) {
        this.loanRepositories = loanRepositories;
        this.bookRepositories = bookRepositories;
        this.partnerRepositories = partnerRepositories;
    }

    public Loan registerLoan(String bookId, String partnerId){
        String loanId = UUID.randomUUID().toString();
        requiredNotBlank(bookId, "El id del libro no puede estar vacio");
        requiredNotBlank(partnerId, "El id del asociado no puede estar vacio");
        Book book = this.bookRepositories.findById(bookId);
        if (book == null){
            throw new BookNotFoundException("El libro no existe");
        }
        Partner partner = this.partnerRepositories.findById(partnerId);
        if (partner == null){
            throw new PartnerNotFoundException("El asociado no existe");
        }

        if (book.getNumberOfCopies() <= 0){
            throw new NoAvailableCopiesException("El libro no tiene copias disponibles");
        }

        // Obtengo el numero de prestamos activos de un asociado
        long activeLoans = this.loanRepositories.findAll().stream().filter(l -> l.getPartnerId().equals(partnerId) && l.getStatusLoan().equals(StatusLoan.ACTIVE)).count();
        if (activeLoans >= MAX_ACTIVE_LOANS){
            throw new LoanLimitExceededException("El asociado ya alcanzo el limite de prestamos");
        }

        // Se crea un nuevo prestamo
        Loan loan = new Loan(loanId, bookId, partnerId, LocalDate.now(), LocalDate.now().plusDays(14), StatusLoan.ACTIVE);
        this.loanRepositories.create(loan);
        // Se actualiza el numero de copias del libro que se esta prestando
        book.setNumberOfCopies(book.getNumberOfCopies() - 1);
        // Se manda una actualizacion para que cuando cambiemos a un json o una DB se pueda escuchar el setteo
        this.bookRepositories.update(book);

        return loan;
    }

    public void registerReturn(String loanId){
        requiredNotBlank(loanId, "El id del prestamo no puede estar vacio");
        Loan loan = this.loanRepositories.findById(loanId);
        if (loan == null){
            throw new LoanNotFoundException("El prestamo no existe");
        }
        if (loan.getStatusLoan() != StatusLoan.ACTIVE){
            throw new LoanAlreadyReturnedException("El libro ya fue devuelto anteriormente");
        }
        // Actualizo el estatus a devuelto
        loan.setStatusLoan(StatusLoan.RETURNED);
        this.loanRepositories.update(loan);

        // Actualizo el numero de copias del libro
        Book book = this.bookRepositories.findById(loan.getBookId());
        book.setNumberOfCopies(book.getNumberOfCopies() + 1);
        this.bookRepositories.update(book);
    }

    public List<Loan> findActiveLoansByPartner(String partnerId){
        requiredNotBlank(partnerId, "El id del asociado no puede estar vacio");
        if (this.partnerRepositories.findById(partnerId) == null){
            throw new PartnerNotFoundException("El asociado no existe");
        }
        return this.loanRepositories.findAll().stream().filter(l -> l.getPartnerId().equals(partnerId) && l.getStatusLoan().equals(StatusLoan.ACTIVE)).toList();
    }

    public List<Loan> listOverdueLoans(){
        LocalDate today = LocalDate.now();
        return this.loanRepositories.findAll().stream().filter(l -> l.getStatusLoan().equals(StatusLoan.ACTIVE) && today.isAfter(l.getDueDate())).toList();
    }

    private void requiredNotBlank(String value, String message){
        if (value.isBlank()){
            throw new IllegalArgumentException(message);
        }
    }
}
