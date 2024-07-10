package com.neeraj.scm.globalExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundExceptionByNeeraj.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundExceptionByNeeraj(ResourceNotFoundExceptionByNeeraj exception){
      String messsage=exception.getMessage();
     ApiResponse apiResponse= ApiResponse.builder().message(messsage).success(true).status(HttpStatus.NOT_FOUND).build();
     return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
  }



}
