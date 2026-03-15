package edu.bi.springdemo.Service;

import edu.bi.springdemo.DTO.LoanDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.entity.Loan;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.repository.BookRepository;
import edu.bi.springdemo.repository.LoanRepository;
import edu.bi.springdemo.repository.UserRepository;
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

    public Loan save(LoanDTO loanDTO){
        Book book = bookRepository.findById(loanDTO.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(loanDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

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
