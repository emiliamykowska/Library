package edu.bi.springdemo.controller;

import com.google.gson.Gson;
import edu.bi.springdemo.exception.DuplicatedDataException;
import edu.bi.springdemo.exception.LoginPasswordException;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//this will catch exception globally
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(IllegalAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleIllegalAccessException(IllegalAccessException e) {
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("timestamp", new Date().toString());
        return map;
    }

    @ExceptionHandler(LoginPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleLoginPasswordException(LoginPasswordException e){
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("timestamp", new Date().toString());
        return map;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException e){
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("timestamp", new Date().toString());
        return map;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, String> map = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                map.put(error.getField(), error.getDefaultMessage())
        );
        map.put("timestamp", new Date().toString());
        return map;
    }

    @ExceptionHandler(NotValidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotValidArgumentException(NotValidArgumentException e){
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("timestamp", new Date().toString());
        return map;
    }

    @ExceptionHandler(DuplicatedDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDuplicatedDataException(DuplicatedDataException e){
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("timestamp", new Date().toString());
        return map;
    }
}
