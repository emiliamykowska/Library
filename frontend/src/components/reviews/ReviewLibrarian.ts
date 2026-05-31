import type { Review } from "./Review";

export interface LibrarianReview extends Review {
    userId: number;
}