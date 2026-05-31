import type { AxiosInstance, AxiosResponse, AxiosError } from "axios";
import type { Review } from "../components/reviews/Review";
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


}