package dev.romario.proyecto;

import dev.romario.proyecto.controllers.BookController;
import dev.romario.proyecto.controllers.LoanController;
import dev.romario.proyecto.controllers.PartnerController;
import dev.romario.proyecto.models.Book;
import dev.romario.proyecto.models.Loan;
import dev.romario.proyecto.models.Partner;
import dev.romario.proyecto.repositories.RepositoryMemory;
import dev.romario.proyecto.services.BookService;
import dev.romario.proyecto.services.LoanService;
import dev.romario.proyecto.services.PartnerService;
import dev.romario.proyecto.views.BookMenu;
import dev.romario.proyecto.views.LoanMenu;
import dev.romario.proyecto.views.PartnerMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class AppContext {
    private final BookMenu bookMenu;
    private final PartnerMenu partnerMenu;
    private final LoanMenu loanMenu;

    public AppContext(Scanner scanner) {
        RepositoryMemory<Book> bookRepositoryMemory = new RepositoryMemory<>(new ArrayList<>());
        BookService bookService = new BookService(bookRepositoryMemory);
        BookController bookController = new BookController(bookService);
        this.bookMenu = new BookMenu(bookController, scanner);

        RepositoryMemory<Partner> partnerRepositoryMemory = new RepositoryMemory<>(new ArrayList<>());
        PartnerService partnerService = new PartnerService(partnerRepositoryMemory);
        PartnerController partnerController = new PartnerController(partnerService);
        this.partnerMenu = new PartnerMenu(partnerController, scanner);

        RepositoryMemory<Loan> loanRepositoriesMemory = new RepositoryMemory<>(new ArrayList<>());
        LoanService loanService = new LoanService(loanRepositoriesMemory, bookRepositoryMemory, partnerRepositoryMemory);
        LoanController loanController = new LoanController(loanService);
        this.loanMenu = new LoanMenu(loanController, scanner);
    }

    public BookMenu getBookMenu() { return this.bookMenu; }

    public PartnerMenu getPartnerMenu() {
        return this.partnerMenu;
    }

    public LoanMenu getLoanMenu() {
        return this.loanMenu;
    }
}
