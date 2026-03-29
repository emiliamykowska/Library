package edu.bi.springdemo.service;

import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.entity.Review;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import edu.bi.springdemo.repository.BookRepository;
import edu.bi.springdemo.repository.ReviewRepository;
import edu.bi.springdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository){
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Review save(ReviewDTO reviewDTO){
        Book book = bookRepository.findById(reviewDTO.getBookId()).orElseThrow(() -> ResourceNotFoundException.create("Book not found"));
        User user = userRepository.findById(reviewDTO.getUserId()).orElseThrow(() -> ResourceNotFoundException.create("User not found"));

        Integer rating = reviewDTO.getRating();

        if (rating > 10 || rating < 0){
            throw NotValidArgumentException.create("Rating has to be an integer between 0 and 10");
        }
        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setReviewDate(reviewDTO.getReviewDate());

        return reviewRepository.save(review);
    }

    public Iterable<Review> findAll(){
        return reviewRepository.findAll();
    }
}
