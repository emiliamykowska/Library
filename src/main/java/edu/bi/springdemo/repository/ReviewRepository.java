package edu.bi.springdemo.repository;

import edu.bi.springdemo.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    Optional<Review> findByUserIdAndBookId(Integer userId, Integer bookId);
}
