package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.Mapper.ReviewMapper;
import edu.bi.springdemo.Service.ReviewService;
import edu.bi.springdemo.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public @ResponseBody ReviewDTO addReview(@RequestBody ReviewDTO reviewDTO){
        Review savedReview = reviewService.save(reviewDTO);
        return reviewMapper.toDto(savedReview);
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
