import BookCard from "./BookCard";
import "../css_files/List.css";
import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import { useApi } from "../../ApiProvider";
import { useEffect, useState } from "react";
import type { Book } from "./Book";

function BookList() {
    const [isLibrarian] = useState<boolean>(
        localStorage.getItem("role") === "LIBRARIAN"
    );

    const apiClient = useApi();
    const [books, setBooks] = useState<Book[]>([]);

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

    return (
        <div className="list-form">
            <h1>List of Books</h1>

            {isLibrarian && (
                <Button
                    variant="contained"
                    component={Link}
                    to="/books/add"
                >
                    Add Book
                </Button>
            )}

            {books.map((book) => (
                <BookCard
                    key={book.bookId}
                    book={book}
                    isLibrarian={isLibrarian}
                />
            ))}

        </div>
    );
}

export default BookList;
