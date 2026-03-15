package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.LoanDTO;
import edu.bi.springdemo.Mapper.LoanMapper;
import edu.bi.springdemo.Service.LoanService;
import edu.bi.springdemo.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan addLoan(@RequestBody LoanDTO loanDTO) {
        Loan loan = loanMapper.toEntity(loanDTO);

        return loanService.save(loan);
    }

    @GetMapping
    public @ResponseBody Iterable<Loan> getAllLoans(){
        return loanService.findAll();
    }
}
