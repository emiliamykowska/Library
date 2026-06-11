package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.BookDTO;
import edu.bi.springdemo.DTO.LoanDTO;
import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import edu.bi.springdemo.mapper.LoanMapper;
import edu.bi.springdemo.service.LoanService;
import edu.bi.springdemo.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;
    private final LoanMapper loanMapper;

    @Autowired
    public LoanController(LoanService loanService, LoanMapper loanMapper) {
        this.loanService = loanService;
        this.loanMapper = loanMapper;
    }

//    @PostMapping
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public @ResponseBody LoanDTO addLoan(@Validated @RequestBody LoanDTO loanDTO) {
//        Loan savedLoan = loanService.save(loanDTO);
//        return loanMapper.toDto(savedLoan);
//    }

    @PostMapping("/borrow")
    public LoanDTO borrow(@Validated @RequestBody LoanDTO loanDTO){
        return loanMapper.toDto(loanService.borrowBook(loanDTO));
    }

    @PostMapping("/borrow/{userId}")
    public LoanDTO borrowAsLibrarian(@PathVariable Integer userId,
                                     @Validated @RequestBody LoanDTO loanDTO) {
        loanDTO.setUserId(userId);
        return loanMapper.toDto(loanService.borrowBookAsLibrarian(loanDTO));
    }

    @PatchMapping("/return/{loanId}")
    public LoanDTO returnBook(@PathVariable Integer loanId, @RequestBody LoanDTO loanDTO){
        return loanMapper.toDto(loanService.returnBook(loanId, loanDTO));
    }

    @GetMapping
    public @ResponseBody Iterable<LoanDTO> getAllLoans(){
        List<LoanDTO> result = new ArrayList<>();

        for (Loan loan : loanService.findAll()){
            LoanDTO dto = loanMapper.toDto(loan);
            dto.setBookTitle(loan.getBook().getTitle());
            dto.setUsername(loan.getUser().getUsername());
            result.add(dto);
        }
        return result;
    }

    @GetMapping("/my")
    public List<LoanDTO> getMyLoans() {
        List<LoanDTO> result = new ArrayList<>();

        List<Loan> userLoans = loanService.getLoansForCurrentUser();

        if(userLoans.isEmpty()){
            throw ResourceNotFoundException.create("You haven't loaned any books yet");
        }

        for (Loan loan : userLoans){
            LoanDTO dto = loanMapper.toDto(loan);
            dto.setBookTitle(loan.getBook().getTitle());
            result.add(dto);
        }
        return result;
    }

    @GetMapping("/user/{userId}")
    public List<LoanDTO> getLoansByUser(@PathVariable Integer userId) {
        List<LoanDTO> result = new ArrayList<>();

        List<Loan> usersLoans = loanService.getLoansByUserId(userId);

        if (usersLoans.isEmpty()){
            throw ResourceNotFoundException.create("This user did not loan any books");
        }

        for (Loan loan : usersLoans){
            LoanDTO dto = loanMapper.toDto(loan);
            dto.setBookTitle(loan.getBook().getTitle());
            result.add(dto);
        }
        return result;
    }
}
