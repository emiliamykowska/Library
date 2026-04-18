package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.LoanDTO;
import edu.bi.springdemo.DTO.ReviewDTO;
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
    public @ResponseBody Iterable<ReviewDTO> getAllReviews(){
        List<ReviewDTO> result = new ArrayList<>();

        for (Review review : reviewService.findAll()){
            result.add(reviewMapper.toDto(review));
        }

        return result;
    }
}
