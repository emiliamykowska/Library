package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.BookDTO;
import edu.bi.springdemo.Mapper.BookMapper;
import edu.bi.springdemo.Service.BookService;
import edu.bi.springdemo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books") //so every endpoint contains book at the beginning
public class BookController {

//    private final BookRepository bookRepository;
    private final BookService bookService;
    private final BookMapper bookMapper;

//    @Autowired //so spring knows it has to create classes
//    public BookController(BookRepository bookRepository){
//        this.bookRepository = bookRepository;
//    }

    @Autowired //so spring knows it has to create classes
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

//    @PostMapping("/add")
//    @ResponseStatus(code = HttpStatus.CREATED) //code 201 if POST succeeded
//    public @ResponseBody Book addBook(@RequestBody Book book) {
//        return bookRepository.save(book);
//    }
//
//    @GetMapping("/getAll")
//    public @ResponseBody Iterable<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED) //code 201 if POST succeeded
    public @ResponseBody Book addBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        return bookService.save(book);

    }

    @GetMapping
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookService.findAll();
    }
}
