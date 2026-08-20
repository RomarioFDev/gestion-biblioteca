package dev.romario.proyecto.models;

import dev.romario.proyecto.repositories.Identifiable;

import java.time.LocalDate;

public class Loan implements Identifiable {
    private String id;
    private String bookId;
    private String partnerId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private StatusLoan statusLoan;

    public Loan(String id, String bookId, String partnerId, LocalDate loanDate, LocalDate dueDate, StatusLoan statusLoan) {
        this.id = id;
        this.bookId = bookId;
        this.partnerId = partnerId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.statusLoan = statusLoan;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public StatusLoan getStatusLoan() {
        return statusLoan;
    }

    public void setStatusLoan(StatusLoan statusLoan) {
        this.statusLoan = statusLoan;
    }
}
