package edu.bi.springdemo.mapper;

import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review){
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setBookId(review.getBook().getBookId());
        reviewDTO.setUserId(review.getUser().getUserId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setReviewDate(review.getReviewDate());

        return reviewDTO;
    }
}
