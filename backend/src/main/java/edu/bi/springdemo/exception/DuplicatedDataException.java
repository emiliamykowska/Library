package edu.bi.springdemo.exception;

public class DuplicatedDataException extends RuntimeException {
    private DuplicatedDataException(String message) {
        super(message);
    }

    public static DuplicatedDataException create(String message){
        return new DuplicatedDataException(message);
    }
}
