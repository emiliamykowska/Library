package edu.bi.springdemo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer loanId;

    @ManyToOne
    @JoinColumn(name = "book_id") //this columns stores the foreign key, it creates book_id column inside Loan table and provides there book's id (coz it's provided lower, like Book book)
    private Book book; //foreign key

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //foreign key

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
