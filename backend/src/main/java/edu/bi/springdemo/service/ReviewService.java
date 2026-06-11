package edu.bi.springdemo.service;

import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.entity.Loan;
import edu.bi.springdemo.entity.Review;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import edu.bi.springdemo.repository.BookRepository;
import edu.bi.springdemo.repository.ReviewRepository;
import edu.bi.springdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Review addReviewAsLibrarian(ReviewDTO reviewDTO){
        Book book = bookRepository.findById(reviewDTO.getBookId()).orElseThrow(() -> ResourceNotFoundException.create("Book not found"));
        User user = userRepository.findById(reviewDTO.getUserId()).orElseThrow(() -> ResourceNotFoundException.create("User not found"));

        if (reviewRepository.findByUserIdAndBookId(user.getUserId(), book.getBookId()).isPresent()) {
            throw NotValidArgumentException.create("User already reviewed this book");
        }

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

    @Transactional
    public Review addReview(ReviewDTO reviewDTO){
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        Book book = bookRepository.findById(reviewDTO.getBookId()).orElseThrow(() -> ResourceNotFoundException.create("Book not found"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> ResourceNotFoundException.create("User not found"));

        if (reviewRepository.findByUserIdAndBookId(user.getUserId(), book.getBookId()).isPresent()) {
            throw NotValidArgumentException.create("User already reviewed this book");
        }

        Integer rating = reviewDTO.getRating();

        if (rating > 10 || rating < 0){
            throw NotValidArgumentException.create("Rating has to be an integer between 0 and 10");
        }

        LocalDate reviewDate = LocalDate.now();

        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setReviewDate(reviewDate);

        return reviewRepository.save(review);
    }

    public Iterable<Review> findAll(){
        return reviewRepository.findAll();
    }

    public List<Review> findBookReviews(String title){
        return reviewRepository.findByBookTitleContainingIgnoreCase(title);
    }

    public void delete(Integer id){
        if (!reviewRepository.existsById(id)) {
            throw ResourceNotFoundException.create("Review with that id was not found");
        }
        reviewRepository.deleteById(id);
    }

    @Transactional
    public Review update(Integer id, ReviewDTO reviewDTO){
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("Review not found"));

        Integer userId = reviewDTO.getUserId() != null ? reviewDTO.getUserId() : review.getUser().getUserId();

        Integer bookId = reviewDTO.getBookId() != null ? reviewDTO.getBookId() : review.getBook().getBookId();

        Optional<Review> existing = reviewRepository
                .findByUserIdAndBookId(userId, bookId);

        if (existing.isPresent() && !existing.get().getReviewId().equals(id)) {
            throw NotValidArgumentException.create("User already reviewed this book");
        }

        if (reviewDTO.getBookId() != null) {
            Book book = bookRepository.findById(reviewDTO.getBookId())
                    .orElseThrow(() -> ResourceNotFoundException.create("Book not found"));
            review.setBook(book);
        }

        if (reviewDTO.getUserId() != null) {
            User user = userRepository.findById(reviewDTO.getUserId())
                    .orElseThrow(() -> ResourceNotFoundException.create("User not found"));
            review.setUser(user);
        }

        if (reviewDTO.getRating() != null) {
            Integer rating = reviewDTO.getRating();

            if (rating > 10 || rating < 0){
                throw NotValidArgumentException.create("Rating has to be between 0 and 10");
            }

            review.setRating(rating);
        }


        if (reviewDTO.getComment() != null){
            review.setComment(reviewDTO.getComment());
        }

        if (reviewDTO.getReviewDate() != null){
            review.setReviewDate(reviewDTO.getReviewDate());
        }

        return reviewRepository.save(review);
    }

    public Review findById(Integer id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("Review with that id was not found"));
    }

    @Transactional
    public void reassignUserReviews(Integer userToDeleteId, User placeholderUser) {
        List<Review> userReviews = reviewRepository.findByUserUserId(userToDeleteId);
        for (Review review : userReviews) {
            review.setUser(placeholderUser);
        }
        reviewRepository.saveAll(userReviews);
    }

}
