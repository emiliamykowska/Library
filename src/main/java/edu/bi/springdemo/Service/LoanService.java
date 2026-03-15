package edu.bi.springdemo.Service;

import edu.bi.springdemo.entity.Loan;
import edu.bi.springdemo.repository.LoanRepository;

public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository){
        this.loanRepository = loanRepository;
    }

    public Loan save(Loan loan){
        return loanRepository.save(loan);
    }

    public Iterable<Loan> findAll(){
        return loanRepository.findAll();
    }
}
