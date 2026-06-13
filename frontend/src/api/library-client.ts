import type { AxiosError, AxiosInstance, AxiosResponse } from "axios";
import axios from "axios";
import type { LoginDto, LoginResponseDto } from "../dto/login.dto";
import { BookClient } from "./book-client";
import { ReviewClient } from "./review-client";
import { UserClient } from "./user-client";
import { LoanClient } from "./loan-client";
import { toast } from "react-toastify";

export type ClientResponse<T> = {
    success: boolean;
    data: T;
    statusCode: number;
}


export class LibraryClient {
    private client: AxiosInstance;
    public books: BookClient;
    public reviews: ReviewClient;
    public users: UserClient;
    public loans: LoanClient;

    constructor() {
        this.client = axios.create({
            baseURL: 'http://localhost:8080'
        })

        this.client.interceptors.request.use((config) => { // changed it to interceptor so it is dynamic 
            const token = localStorage.getItem("token");
            if (token) {
                config.headers['Authorization'] = `Bearer ${token}`;
            }
            return config;
        }, (error) => {          

            return Promise.reject(error);
        });

        this.client.interceptors.response.use(
            (response) => {
                return response
            },
            (error) => {
                if (error.response && error.response.data) {
                    const errorData = error.response.data;
                    
                    if (errorData.message) {
                        toast.error(errorData.message);
                    }             
                    else {                
                        Object.keys(errorData).forEach((field) => {                        
                            if (field !== "timestamp") {
                                toast.error(`${field}: ${errorData[field]}`);
                            }                     
                        });
                    }
                } else {                
                    toast.error("Unable to connect to the server");
                }

                return Promise.reject(error);
            }

        )

        this.books = new BookClient(this.client);
        this.reviews = new ReviewClient(this.client);
        this.users = new UserClient(this.client);
        this.loans = new LoanClient(this.client);
    }

    public async login(data: LoginDto): Promise<ClientResponse<LoginResponseDto | null>> {
        try {
            const response: AxiosResponse<LoginResponseDto> = await this.client.post(
                '/login',
                data
            );

            if (response.data.token) {
                localStorage.setItem("token", response.data.token); // to store the token even after refresh               

                if (response.data.role) {
                    localStorage.setItem("role", response.data.role); // same with role
                }

                this.client.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.token;
            }

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
                statusCode: axiosError.response?.status || 0 //because 0 is not a valid code 
            }
        }
    }
}