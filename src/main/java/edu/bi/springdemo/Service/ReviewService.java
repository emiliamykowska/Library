package edu.bi.springdemo.Service;

import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.entity.Review;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.repository.BookRepository;
import edu.bi.springdemo.repository.ReviewRepository;
import edu.bi.springdemo.repository.UserRepository;
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

    public Review save(ReviewDTO reviewDTO){
        Book book = bookRepository.findById(reviewDTO.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(reviewDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

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
