package edu.bi.springdemo.repository;

import edu.bi.springdemo.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> { //integer is type of the primary key
    List<Book> findByTitleContainingIgnoreCase(String title);
}
