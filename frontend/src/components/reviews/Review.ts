export interface Review {
    reviewId: number;
    bookId: number;
    bookTitle: string;
    rating: number;
    comment: string;
    reviewDate?: string;
}