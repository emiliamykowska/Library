import type { Book } from "../books/Book";
import GeneralCard from "../GeneralCard";
import { Typography, IconButton, Box } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

interface BookCardProps {
    book: Book;
    isLibrarian?: boolean;
}


function BookCard(
    { book, isLibrarian
    }: BookCardProps
) {
    return (
        <GeneralCard>
            <Typography variant="h5">
                {book.title}
            </Typography>
            <Typography variant="h6">
                {book.author}
            </Typography>
            <Typography>
                ISBN number: {book.isbn}
            </Typography>
            <Typography>
                Publisher: {book.publisher}
            </Typography>
            <Typography>
                Year: {book.year}
            </Typography>
            <Typography
                sx={{
                    color: book.availableCopies === 0 ? "red" : "inherit"
                }}>
                Available copies: {book.availableCopies}
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

export default BookCard;