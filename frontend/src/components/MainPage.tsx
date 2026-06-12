import "./css_files/main-page.css";
import { Box, Typography, Paper, Button } from "@mui/material";
import { Link } from "react-router-dom";
import BookCard from "./books/BookCard";
import type { Book } from "./books/Book";
import type { User } from "./users/User";
import type { Review } from "./reviews/Review";
import { useEffect, useState } from "react";
import { useApi } from "../ApiProvider";

function MainPage() {
    const [isLoggedIn] = useState<boolean>(localStorage.getItem("token") != null)

    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const apiClient = useApi()

    const [books, setBooks] = useState<Book[]>([]);
    const [users, setUsers] = useState<User[]>([]);
    const [reviews, setReviews] = useState<Review[]>([]);

    useEffect(() => {
        apiClient.books.getBooks()
            .then((response) => {
                if (response.success && response.data) {
                    setBooks(response.data)
                }
            }
            )
    },
        [apiClient])

    useEffect(() => {
        apiClient.users.getUsers()
            .then((response) => {
                if (response.success && response.data) {
                    setUsers(response.data)
                }
            }
            )
    },
        [apiClient])

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
        <Box className="main-page">

            <h1>Library Home</h1>

            <Box className="stats-row">
                <Paper className="stat-card">
                    <Typography variant="h5" sx={{ fontWeight: 700 }}>{books.length}</Typography>
                    <Typography color="textSecondary">Books</Typography>
                </Paper>

                {isLibrarian && 
                <Paper className="stat-card">
                    <Typography variant="h5" sx={{ fontWeight: 700 }}>{users.length}</Typography>
                    <Typography color="textSecondary">Users</Typography>
                </Paper>
                }               

                <Paper className="stat-card">
                    <Typography variant="h5" sx={{ fontWeight: 700 }}>{reviews.length}</Typography>
                    <Typography color="textSecondary">Reviews</Typography>
                </Paper>
            </Box>

            <h2>
                Exemplary Available Books
            </h2>

            <Box className="books-column">
                {books
                    .filter(book => book.availableCopies > 0)
                    .slice(0, 5)
                    .map(book => (
                        <BookCard key={book.bookId} book={book} isLibrarian={isLibrarian} />
                    ))}
            </Box>

            <Button
                variant="contained"
                component={Link}
                to="/books"
                sx={{backgroundColor: "#8b6f4f"}}      
            >
                View All Books
            </Button>

            {!isLoggedIn && (
                <Button
                    variant="contained"
                    component={Link}
                    to="/login"
                    sx={{backgroundColor: "#8b6f4f"}}
                >
                    LogIn
                </Button>
            )}


        </Box>
    );
}

export default MainPage;