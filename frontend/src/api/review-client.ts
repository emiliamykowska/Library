import type { AxiosInstance, AxiosResponse, AxiosError } from "axios";
import type { Review } from "../components/reviews/Review";
import type { ReviewFormValues } from "../components/reviews/ReviewFormValues";
import type { ClientResponse } from "./library-client";


export class ReviewClient {
    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    public async getReviews(): Promise<ClientResponse<Review[] | null>> {

        try {
            const response: AxiosResponse<Review[]> = await this.client.get(
                '/reviews'
            );

            return {
                success: true,
                data: response.data,
                statusCode: response.status
            };
        }
        catch (error) {
            const axiosError = error as AxiosError<Error>;

            return {
                success: false,
                data: null,
                statusCode: axiosError.response?.status || 0
            }
        }
    }

    public async getReview(reviewId: number): Promise<ClientResponse<Review | null>> {
    
            try {
                const response: AxiosResponse<Review> = await this.client.get(
                    `/reviews/${reviewId}`
                );
    
                return {
                    success: true,
                    data: response.data,
                    statusCode: response.status
                };
            }
            catch (error) {
                const axiosError = error as AxiosError<Error>;
    
                return {
                    success: false,
                    data: null,
                    statusCode: axiosError.response?.status || 0
                }
            }
        }

    public async addReview(review: ReviewFormValues): Promise<ClientResponse<Review | null>> {
    
            try {
                const response: AxiosResponse<Review> = await this.client.post(
                    '/reviews',
                    review
                );
    
                return {
                    success: true,
                    data: response.data,
                    statusCode: response.status
                };
            }
            catch (error) {
                const axiosError = error as AxiosError<Error>;
    
                return {
                    success: false,
                    data: null,
                    statusCode: axiosError.response?.status || 0
                };
            }
        }

        public async addReviewForUser(userId: number, review: ReviewFormValues): Promise<ClientResponse<Review | null>> {
    
            try {
                const response: AxiosResponse<Review> = await this.client.post(
                    `/reviews/${userId}`,
                    review
                );
    
                return {
                    success: true,
                    data: response.data,
                    statusCode: response.status
                };
            }
            catch (error) {
                const axiosError = error as AxiosError<Error>;
    
                return {
                    success: false,
                    data: null,
                    statusCode: axiosError.response?.status || 0
                };
            }
        }
    
        public async updateReview(reviewId: number, review: Partial<ReviewFormValues>): Promise<ClientResponse<Review | null>> {
    
            try {
                const response: AxiosResponse<Review> = await this.client.patch(
                    `/reviews/${reviewId}`,
                    review
                );
    
                return {
                    success: true,
                    data: response.data,
                    statusCode: response.status
                };
            }
            catch (error) {
                const axiosError = error as AxiosError<Error>;
    
                return {
                    success: false,
                    data: null,
                    statusCode: axiosError.response?.status || 0
                };
            }
        }
    
        public async deleteReview(reviewId: number): Promise<ClientResponse<null>> {
    
            try {
                const response: AxiosResponse<void> = await this.client.delete(
                    `/reviews/${reviewId}`
                );
    
                return {
                    success: true,
                    data: null,
                    statusCode: response.status
                };
            }
            catch (error) {
                const axiosError = error as AxiosError<Error>;
    
                return {
                    success: false,
                    data: null,
                    statusCode: axiosError.response?.status || 0
                };
            }
        }


}