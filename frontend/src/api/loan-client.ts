import type { AxiosInstance, AxiosResponse, AxiosError } from "axios";
import type { Loan } from "../components/loans/Loan";
import type { ClientResponse } from "./library-client";


export class LoanClient {
    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    public async getLoans(): Promise<ClientResponse<Loan[] | null>> {

        try {
            const response: AxiosResponse<Loan[]> = await this.client.get(
                "/loans"
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

    public async getMyLoans(): Promise<ClientResponse<Loan[] | null>> {
        try {
            const response: AxiosResponse<Loan[]> = await this.client.get("/loans/my");
            return {
                success: true,
                data: response.data,
                statusCode: response.status
            };
        } catch (error) {
            const axiosError = error as AxiosError<Error>;
            return {
                success: false,
                data: null,
                statusCode: axiosError.response?.status || 0
            };
        }
    }
}