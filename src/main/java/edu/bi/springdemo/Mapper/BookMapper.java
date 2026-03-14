package edu.bi.springdemo.Mapper;

import edu.bi.springdemo.DTO.BookDTO;
import edu.bi.springdemo.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookDTO toDto(Book book) {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setYear(book.getYear());
        bookDTO.setAvailableCopies(book.getAvailableCopies());

        return bookDTO;
    }

    public Book toEntity(BookDTO bookDTO) {
        Book book = new Book();

        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setYear(bookDTO.getYear());
        book.setAvailableCopies(bookDTO.getAvailableCopies());

        return book;
    }
}
