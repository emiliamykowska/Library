package edu.bi.springdemo.service;

import edu.bi.springdemo.DTO.LoanDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.entity.Loan;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.enums.UserRole;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import edu.bi.springdemo.repository.BookRepository;
import edu.bi.springdemo.repository.LoanRepository;
import edu.bi.springdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository){
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Loan borrowBookAsLibrarian(LoanDTO loanDTO){
        LocalDate loanDate = loanDTO.getLoanDate() != null ? loanDTO.getLoanDate() : LocalDate.now();

        LocalDate dueDate = loanDTO.getDueDate() != null ? loanDTO.getDueDate() : loanDate.plusDays(30);

        if (dueDate.isBefore(loanDate)) {
            throw NotValidArgumentException.create("Due date cannot be before loan date");
        }

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> ResourceNotFoundException.create("Book with that id was not found"));

        User user = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() -> ResourceNotFoundException.create("User with that id was not found"));

        if (book.getAvailableCopies() <= 0){
            throw NotValidArgumentException.create("No available copies");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan borrowBook(LoanDTO loanDTO) {

        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> ResourceNotFoundException.create("User not found"));

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> ResourceNotFoundException.create("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw NotValidArgumentException.create("No available copies");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(30);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(loanDate);
        loan.setDueDate(dueDate);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnBook(Integer loanId, LoanDTO loanDTO){
        String username = Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication()
        ).getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> ResourceNotFoundException.create("User not found"));

        UserRole currentRole = currentUser.getRole();

        if (currentRole.equals(UserRole.READER) || loanDTO.getReturnDate() == null){
            loanDTO.setReturnDate(LocalDate.now());

        }

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> ResourceNotFoundException.create("Loan with that id was not found"));


        if (currentRole.equals(UserRole.READER) && !loan.getUser().getUserId().equals(currentUser.getUserId())) {
            throw NotValidArgumentException.create("You are not allowed to return somebody else's loan");
        }

        if (loan.getReturnDate() != null){
            throw NotValidArgumentException.create("Book already returned");
        }

        if (loanDTO.getReturnDate().isBefore(loan.getLoanDate())) {
            throw NotValidArgumentException.create("Return date cannot be before loan date");
        }

        loan.setReturnDate(loanDTO.getReturnDate());

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return loanRepository.save(loan);
    }

    public Iterable<Loan> findAll(){
        return loanRepository.findAll();
    }

    public List<Loan> getLoansForCurrentUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> ResourceNotFoundException.create("User not found"));

        return loanRepository.findByUserUserId(user.getUserId());
    }

    public List<Loan> getLoansByUserId(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> ResourceNotFoundException.create("User not found"));

        return loanRepository.findByUserUserId(userId);
    }

    @Transactional
    public void reassignUserLoans(Integer userToDeleteId, User placeholderUser) {
        List<Loan> userLoans = loanRepository.findByUserUserId(userToDeleteId);
        for (Loan loan : userLoans) {
            loan.setUser(placeholderUser);
        }
        loanRepository.saveAll(userLoans);
    }

    @Transactional
    public void delete(Integer id){

        Loan loanToDelete = loanRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("Loan with that id was not found"));

        if (loanToDelete.getReturnDate() == null){
            throw NotValidArgumentException.create("Cannot delete loan with not returned book");
        }

        loanRepository.delete(loanToDelete);
    }
}
