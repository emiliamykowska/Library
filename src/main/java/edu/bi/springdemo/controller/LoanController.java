package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.LoanDTO;
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

    @PatchMapping("/{id}/return")
    public LoanDTO returnBook(@PathVariable Integer id, @RequestBody LoanDTO loanDTO){
        return loanMapper.toDto(loanService.returnBook(id, loanDTO));
    }

    @GetMapping
    public @ResponseBody Iterable<LoanDTO> getAllLoans(){
        List<LoanDTO> result = new ArrayList<>();

        for (Loan loan : loanService.findAll()){
            result.add(loanMapper.toDto(loan));
        }
        return result;
    }
}
