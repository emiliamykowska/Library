import type { AxiosInstance, AxiosResponse, AxiosError } from "axios";
import type { User } from "../components/users/User";
import type { UserFormValues } from "../components/users/UserFormValues";
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

    public async getUser(userId: number): Promise<ClientResponse<User | null>> {
    
            try {
                const response: AxiosResponse<User> = await this.client.get(
                    `/users/${userId}`
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

    public async addUser(user: UserFormValues): Promise<ClientResponse<User | null>> {
    
            try {
                const response: AxiosResponse<User> = await this.client.post(
                    '/users',
                    user
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
    
        public async updateUser(userId: number, user: Partial<UserFormValues>): Promise<ClientResponse<User | null>> {
    
            try {
                const response: AxiosResponse<User> = await this.client.patch(
                    `/users/${userId}`,
                    user
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
    
        public async deleteUser(userId: number): Promise<ClientResponse<null>> {
    
            try {
                const response: AxiosResponse<void> = await this.client.delete(
                    `/users/${userId}`
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