import type { AxiosInstance, AxiosResponse, AxiosError } from "axios";
import type { Book } from "../components/books/Book";
import type { BookFormValues } from "../components/books/BookFormValues";
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

    public async getBook(bookId: number): Promise<ClientResponse<Book | null>> {

        try {
            const response: AxiosResponse<Book> = await this.client.get(
                `/books/${bookId}`
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

    public async searchBooks(title: string): Promise<ClientResponse<Book[] | null>> {

    try {
        const response: AxiosResponse<Book[]> = await this.client.get(
            `/books/search?title=${encodeURIComponent(title)}`
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

    public async addBook(book: BookFormValues): Promise<ClientResponse<Book | null>> {

        try {
            const response: AxiosResponse<Book> = await this.client.post(
                '/books',
                book
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

    public async updateBook(bookId: number, book: Partial<BookFormValues>): Promise<ClientResponse<Book | null>> {

        try {
            const response: AxiosResponse<Book> = await this.client.patch(
                `/books/${bookId}`,
                book
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

    public async deleteBook(bookId: number): Promise<ClientResponse<null>> {

        try {
            const response: AxiosResponse<void> = await this.client.delete(
                `/books/${bookId}`
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