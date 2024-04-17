package com.example.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 해당 에러로 들어올때 해당 에러코드로 내린다.
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e){
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
    }
}
