import ReviewCard from "./ReviewCard";
import "../css_files/List.css";
import { useApi } from "../../ApiProvider";
import { useEffect, useState } from "react";
import type { Review } from "./Review";
import { useParams } from "react-router-dom";

function ReviewList() {
    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const apiClient = useApi();

    const { bookId } = useParams<{ bookId: string }>();
    const [reviews, setReviews] = useState<Review[]>([]);

    const onDelete = async (reviewId: number) => {
        const result = await apiClient.reviews.deleteReview(reviewId);

        if (result.success) {
            setReviews(reviews =>
                reviews.filter(review => review.reviewId !== reviewId)
            );
        }
    };    
    

    useEffect(() => {
        if (bookId != null) {            
            apiClient.reviews.getReviewByBook(Number(bookId))
                .then((response) => {
                    if (response.success && response.data) {
                        setReviews(response.data);
                    }
                });
        } else {            
            apiClient.reviews.getReviews()
                .then((response) => {
                    if (response.success && response.data) {
                        setReviews(response.data);
                    }
                });
        }
    }, [apiClient, bookId]);


    return (
        <div className="list-form">
            {reviews.length > 0 ? (                
                reviews.map((review) => (
                    <ReviewCard
                        key={review.reviewId}
                        review={review}
                        isLibrarian={isLibrarian}
                        onDelete={onDelete}
                    />
                ))        
            ) : (                   
                <p>
                    This book does not have any reviews yet
                </p>
            )
            }
        </div >

        
    );
}

export default ReviewList;
