package edu.bi.springdemo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book { //how book is stored in the database, with id, relationships etc.; client doesnt controll it

    @Id //means it's primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //so bookId is automatically generated
    private Integer bookId;

    @Column(unique=true) //coz isbn is different for every book, also not null could be specified etc.
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Long year;
    private Long availableCopies;

    @OneToMany(mappedBy = "book") //look for the field book in loan entity
    private List<Loan> loans;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;

    @OneToOne(mappedBy = "book") //it points to BookDetial.book field
    private BookDetail bookDetail;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Long availableCopies) {
        this.availableCopies = availableCopies;
    }
}
