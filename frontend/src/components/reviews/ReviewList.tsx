import ReviewCard from "./ReviewCard";
import "../css_files/List.css";
import { useApi } from "../../ApiProvider";
import { useEffect, useState } from "react";
import type { Review } from "./Review";

function ReviewList() {
    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const apiClient = useApi();

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
        apiClient.reviews.getReviews()
            .then((response) => {
                if (response.success && response.data) {
                    setReviews(response.data)
                }
            }
            )
    },
        [apiClient])


    return (
        <div className="list-form">
            {
                reviews.map((review) => (
                    <ReviewCard
                        key={review.reviewId}
                        review={review}
                        isLibrarian={isLibrarian}
                        onDelete={onDelete}
                    />
                ))
            }
        </div >
    );
}

export default ReviewList;
