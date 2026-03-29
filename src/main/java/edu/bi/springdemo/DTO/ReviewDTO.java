package edu.bi.springdemo.DTO;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

public class ReviewDTO {

    @NotNull
    private Integer bookId;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer rating;

    @NotBlank
    private String comment;

    @NotNull
    private LocalDate reviewDate;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
