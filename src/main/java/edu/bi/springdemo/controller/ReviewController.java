package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.Mapper.ReviewMapper;
import edu.bi.springdemo.Service.ReviewService;
import edu.bi.springdemo.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody Review addReview(@RequestBody ReviewDTO reviewDTO){
        Review review = reviewMapper.toEntity(reviewDTO);
        return reviewService.save(review);
    }

    @GetMapping
    public @ResponseBody Iterable<Review> getAllReviews(){
        return reviewService.findAll();
    }
}
