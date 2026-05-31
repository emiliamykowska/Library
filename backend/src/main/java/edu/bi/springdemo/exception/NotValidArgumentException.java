package edu.bi.springdemo.exception;

public class NotValidArgumentException extends RuntimeException{
    private NotValidArgumentException(String message){
        super(message);
    }

    public static NotValidArgumentException create(String message){
        return new NotValidArgumentException(message);
    }
}
