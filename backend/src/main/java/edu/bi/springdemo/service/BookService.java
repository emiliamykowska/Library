package edu.bi.springdemo.service;

import edu.bi.springdemo.DTO.BookDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import edu.bi.springdemo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Transactional
    public Book update(Integer id, BookDTO bookDTO){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("Book with that id was not found"));

        if (bookDTO.getAvailableCopies() != null) {
            if (bookDTO.getAvailableCopies() < 0) {
                throw NotValidArgumentException.create("Number of available copies cannot be negative");
            }
            book.setAvailableCopies(bookDTO.getAvailableCopies());
        }

        if (bookDTO.getYear() != null) {
            if (bookDTO.getYear() < 0) {
                throw NotValidArgumentException.create("Year cannot be negative");
            }
            book.setYear(bookDTO.getYear());
        }

        if (bookDTO.getTitle() != null){
            book.setTitle(bookDTO.getTitle());
        }

        if (bookDTO.getAuthor() != null){
            book.setAuthor(bookDTO.getAuthor());
        }

        if (bookDTO.getPublisher() != null){
            book.setPublisher(bookDTO.getPublisher());
        }

        if (bookDTO.getIsbn() != null){
            book.setIsbn(bookDTO.getIsbn());
        }

        return bookRepository.save(book);
    }

    public void delete(Integer id){
        if (!bookRepository.existsById(id)) {
            throw ResourceNotFoundException.create("Book with that id was not found");
        }
        bookRepository.deleteById(id);
    }

    public List<Book> findBook(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
