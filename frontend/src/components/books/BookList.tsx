import BookCard from "./BookCard";
import "../css_files/List.css";
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

    const onDelete = async (bookId: number) => {
        const result = await apiClient.books.deleteBook(bookId);

        if (result.success) {
            setBooks(books =>
                books.filter(book => book.bookId !== bookId)
            );
        }
    };

    return (
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
    );
}

export default BookList;
