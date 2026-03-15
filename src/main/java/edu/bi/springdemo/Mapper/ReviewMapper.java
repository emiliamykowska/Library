package edu.bi.springdemo.Mapper;

import edu.bi.springdemo.DTO.ReviewDTO;
import edu.bi.springdemo.entity.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review){
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setBookId(review.getBook().getBookId());
        reviewDTO.setUserId(review.getUser().getUserId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setReviewDate(review.getReviewDate());

        return reviewDTO;
    }
}
