package edu.bi.springdemo.Mapper;

import edu.bi.springdemo.DTO.LoanDTO;
import edu.bi.springdemo.entity.Loan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanMapper {
    public LoanDTO toDto(Loan loan){
        LoanDTO loanDTO = new LoanDTO();

        loanDTO.setBookId(loan.getBook().getBookId());
        loanDTO.setUserId(loan.getUser().getUserId());
        loanDTO.setLoanDate(loan.getLoanDate());
        loanDTO.setDueDate(loan.getDueDate());
        loanDTO.setReturnDate(loanDTO.getReturnDate());

        return loanDTO;
    }

    public Loan toEntity(LoanDTO loanDTO) {
        Loan loan = new Loan();

        loan.setBook(loanDTO.getBookId());
        loan.setUser(loanDTO.getUserId());
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());
        loan.setReturnDate(loanDTO.getReturnDate());

        return loan;
    }
}
