export interface Review {
    reviewId: number;
    userId: number;
    username: string;
    bookId: number;
    bookTitle: string;    
    rating: number;
    comment: string;
    reviewDate?: string;
}