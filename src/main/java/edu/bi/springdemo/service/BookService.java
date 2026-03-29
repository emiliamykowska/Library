package edu.bi.springdemo.service;

import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book save(Book book){
        if (book.getAvailableCopies() < 0){
            throw NotValidArgumentException.create("Number of available copies cannot be negative");
        }

        if (book.getYear() < 0){
            throw NotValidArgumentException.create("Year cannot be negative");
        }

        return bookRepository.save(book);
    }

    public Iterable<Book> findAll(){
        return bookRepository.findAll();
    }
}
