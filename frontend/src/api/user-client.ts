import type { AxiosInstance, AxiosResponse, AxiosError } from "axios";
import type { User } from "../components/users/User";
import type { ClientResponse } from "./library-client";


export class UserClient {
    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    public async getUsers(): Promise<ClientResponse<User[] | null>> {

        try {
            const response: AxiosResponse<User[]> = await this.client.get(
                '/users'
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