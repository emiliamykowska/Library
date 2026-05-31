import type { Review } from "../reviews/Review";
import GeneralCard from "../GeneralCard";
import { Typography, Rating, IconButton, Box } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

interface ReviewCardProps {
    review: Review;
    isLibrarian?: boolean;
}

function ReviewCard(
    { review, isLibrarian }: ReviewCardProps
) {
    return (
        <GeneralCard>
            <Typography variant="h5" sx={{ mb: 2 }}>
                {review.bookTitle}
            </Typography>
            <Rating value={review.rating} max={10} readOnly sx={{ mb: 2 }} />
            <Typography sx={{ mb: 2 }}>
                "{review.comment}"
            </Typography>

            {isLibrarian && (
                <Box>
                    <IconButton>
                        <EditIcon />
                    </IconButton>
                    <IconButton color="error">
                        <DeleteIcon />
                    </IconButton>
                </Box>
            )}

        </GeneralCard >
    )
}

export default ReviewCard;