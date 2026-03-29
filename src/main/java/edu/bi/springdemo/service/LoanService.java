package edu.bi.springdemo.service;

import edu.bi.springdemo.DTO.LoanDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.entity.Loan;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import edu.bi.springdemo.repository.BookRepository;
import edu.bi.springdemo.repository.LoanRepository;
import edu.bi.springdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public Loan save(LoanDTO loanDTO){
        if (loanDTO.getDueDate().isBefore(loanDTO.getLoanDate())) {
            throw NotValidArgumentException.create("Due date cannot be before loan date");
        }

        if (loanDTO.getReturnDate().isBefore(loanDTO.getLoanDate())) {
            throw NotValidArgumentException.create("Return date cannot be before loan date");
        }

        Book book = bookRepository.findById(loanDTO.getBookId()).orElseThrow(() -> ResourceNotFoundException.create("Book not found"));
        User user = userRepository.findById(loanDTO.getUserId()).orElseThrow(() -> ResourceNotFoundException.create("User not found"));

        Loan loan = new Loan();

        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());
        loan.setReturnDate(loanDTO.getReturnDate());

        return loanRepository.save(loan);
    }

    public Iterable<Loan> findAll(){
        return loanRepository.findAll();
    }
}
