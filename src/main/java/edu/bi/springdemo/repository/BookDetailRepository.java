package edu.bi.springdemo.repository;

import edu.bi.springdemo.entity.BookDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDetailRepository extends CrudRepository<BookDetail, Integer> {
}
