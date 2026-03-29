package edu.bi.springdemo.exception;

public class ResourceNotFoundException extends RuntimeException {
    private ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException create(String message){
        return new ResourceNotFoundException(message);
    }
}
