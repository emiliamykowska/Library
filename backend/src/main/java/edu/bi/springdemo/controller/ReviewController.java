package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.BookDTO;
import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.entity.Book;
import edu.bi.springdemo.mapper.ReviewMapper;
import edu.bi.springdemo.service.ReviewService;
import edu.bi.springdemo.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper){
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody ReviewDTO addReview(@Validated @RequestBody ReviewDTO reviewDTO){
        Review savedReview = reviewService.addReview(reviewDTO);
        return reviewMapper.toDto(savedReview);
    }

    @PostMapping("/{userId}")
    public ReviewDTO addAsLibrarian(@PathVariable Integer userId,
                                     @Validated @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setUserId(userId);
        return reviewMapper.toDto(reviewService.addReviewAsLibrarian(reviewDTO));
    }


    @GetMapping
    public Iterable<ReviewDTO> getReviews(@RequestParam(required = false) String title){
        List<ReviewDTO> result = new ArrayList<>();

        Iterable<Review> reviews;

        if (title != null) {
            reviews = reviewService.findBookReviews(title);
        } else {
            reviews = reviewService.findAll();
        }

        for (Review review : reviews){
            ReviewDTO dto = reviewMapper.toDto(review);
            dto.setBookTitle(review.getBook().getTitle());
            dto.setUsername(review.getUser().getUsername());
            result.add(dto);
        }

        return result;
    }

    @PatchMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable Integer id, @RequestBody ReviewDTO reviewDTO){
        return reviewMapper.toDto(reviewService.update(id, reviewDTO));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Integer id){
        reviewService.delete(id);
    }

    @GetMapping("/{id}")
    public @ResponseBody ReviewDTO getReview(@PathVariable Integer id) {
        Review review = reviewService.findById(id);
        return reviewMapper.toDto(review);
    }

    @GetMapping("/book/{bookId}")
    public Iterable<ReviewDTO> getReviewsByBookId(@PathVariable Integer bookId){
        List<ReviewDTO> result = new ArrayList<>();
        Iterable<Review> reviews = reviewService.findReviewsByBookId(bookId);

        for (Review review : reviews){
            ReviewDTO dto = reviewMapper.toDto(review);
            dto.setBookTitle(review.getBook().getTitle());
            dto.setUsername(review.getUser().getUsername());
            result.add(dto);
        }

        return result;
    }
}
