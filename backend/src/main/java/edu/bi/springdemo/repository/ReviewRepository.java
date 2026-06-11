package edu.bi.springdemo.repository;

import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.entity.Loan;
import edu.bi.springdemo.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.user.userId = :userId AND r.book.bookId = :bookId")
    Optional<Review> findByUserIdAndBookId(@Param("userId") Integer userId,
                                       @Param("bookId") Integer bookId);

    List<Review> findByBookTitleContainingIgnoreCase(String title);

    List<Review> findByUserUserId(Integer userId);
}
