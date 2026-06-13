import BookCard from "./BookCard";
import "../css_files/List.css";
import { useApi } from "../../ApiProvider";
import { useEffect, useState } from "react";
import type { Book } from "./Book";
import { TextField, Box } from "@mui/material";

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
    

    const onSearch = async (text: string) => {
        const response = await apiClient.books.searchBooks(text);

        if (response.success && response.data) {
            setBooks(response.data);
        }
    };

    const onDelete = async (bookId: number) => {
        const result = await apiClient.books.deleteBook(bookId);

        if (result.success) {
            setBooks(books =>
                books.filter(book => book.bookId !== bookId)
            );
        }
    };

    return (
        <Box sx={{ width: "100%", p: 2 }}>
            <TextField sx={{borderColor: "#8b6f47", width: "800px"}}
                placeholder="Search books by title"
                variant="outlined"                
                onChange={(e) => onSearch(e.target.value)}
            />
            <div className="list-form">
                {books.map((book) => (
                    <BookCard
                        key={book.bookId}
                        book={book}
                        isLibrarian={isLibrarian}
                        onDelete={onDelete}
                    />
                ))}

            </div>
        </Box>
        );       

}

export default BookList;
