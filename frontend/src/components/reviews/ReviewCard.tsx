import type { Review } from "../reviews/Review";
import GeneralCard from "../GeneralCard";
import { Typography, Rating, IconButton, Box } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { useNavigate } from "react-router-dom";

interface ReviewCardProps {
    review: Review;
    isLibrarian?: boolean;
    onDelete?: (reviewId: number) => void;
}

function ReviewCard(
    { review, isLibrarian, onDelete }: ReviewCardProps
) {
    const navigate = useNavigate();

    return (
        <GeneralCard>
            <Typography variant="h5" sx={{ mb: 2 }}>
                {review.bookTitle}
            </Typography>
            <Typography sx={{ mb: 2 }}>
                {review.username}
            </Typography>
            <Rating value={review.rating} max={10} readOnly sx={{ mb: 2 }} />
            <Typography sx={{ mb: 2 }}>
                "{review.comment}"
            </Typography>

            {isLibrarian && (
                <Box>
                    <IconButton onClick={() => navigate(`/reviews/edit/${review.reviewId}`)}>
                        <EditIcon />
                    </IconButton>
                    <IconButton color="error" onClick={() => onDelete?.(review.reviewId)}>
                        <DeleteIcon />
                    </IconButton>
                </Box>
            )}

        </GeneralCard >
    )
}

export default ReviewCard;