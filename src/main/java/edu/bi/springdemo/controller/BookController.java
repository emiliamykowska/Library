package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.BookDTO;
import edu.bi.springdemo.mapper.BookMapper;
import edu.bi.springdemo.service.BookService;
import edu.bi.springdemo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public @ResponseBody BookDTO addBook(@Validated @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookService.save(book);

        return bookMapper.toDto(savedBook);
    }

    @GetMapping
    public @ResponseBody Iterable<BookDTO> getAllBooks() {
        List<BookDTO> result = new ArrayList<>();

        for (Book book : bookService.findAll()){
            result.add(bookMapper.toDto(book));
        }

        return result;
    }

    @PatchMapping("/{id}")
    public BookDTO updateBook(@PathVariable Integer id, @RequestBody BookDTO bookDTO){
        return bookMapper.toDto(bookService.update(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Integer id){
        bookService.delete(id);
    }
}
