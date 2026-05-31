import ReviewCard from "./ReviewCard";
import "../css_files/List.css";
import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import { useApi } from "../../ApiProvider";
import { useEffect, useState } from "react";
import type { Review } from "./Review";

function ReviewList() {
    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const apiClient = useApi();

    const [reviews, setReviews] = useState<Review[]>([]);


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
            <h1>List of Reviews</h1>

            <Button
                variant="contained"
                component={Link}
                to="/reviews/add"
            >
                Add Review
            </Button>


            {
                reviews.map((review) => (
                    <ReviewCard
                        key={review.reviewId}
                        review={review}
                        isLibrarian={isLibrarian}
                    />
                ))
            }
        </div >
    );
}

export default ReviewList;
