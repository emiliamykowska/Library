import type { AxiosInstance, AxiosResponse, AxiosError } from "axios";
import type { Book } from "../components/books/Book";
import type { ClientResponse } from "./library-client";


export class BookClient {
    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    public async getBooks(): Promise<ClientResponse<Book[] | null>> {

        try {
            const response: AxiosResponse<Book[]> = await this.client.get(
                '/books'
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