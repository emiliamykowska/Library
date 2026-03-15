package edu.bi.springdemo.Service;

import edu.bi.springdemo.entity.Review;
import edu.bi.springdemo.repository.ReviewRepository;

public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public Review save(Review review){
        return reviewRepository.save(review);
    }

    public Iterable<Review> findAll(){
        return reviewRepository.findAll();
    }
}
