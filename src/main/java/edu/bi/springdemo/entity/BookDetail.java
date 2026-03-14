package edu.bi.springdemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class BookDetail {

    @Id
    private Integer bookId;

    @OneToOne
    @MapsId //use id of other entity (book) as this id, it reuses book's id
    @JoinColumn(name = "book_id") //it is PK and FK; book_id refers to book_id column inside bookdetail table
    private Book book;

    @ElementCollection //to store genres in different table coz it's not atomic
    private ArrayList<String> genres;
    private String URL;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
