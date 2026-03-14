package edu.bi.springdemo.repository;

import edu.bi.springdemo.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
