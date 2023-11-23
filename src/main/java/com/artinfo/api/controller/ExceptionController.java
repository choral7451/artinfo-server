package com.artinfo.api.controller;

import com.artinfo.api.exception.ArtinfoException;
import com.artinfo.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        System.out.println("오나ㅣ????");
        ErrorResponse response = ErrorResponse.builder()
          .code("400")
          .message("잘못된 요청입니다.")
          .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        System.out.println(response);
        return response;
    }

    @ResponseBody
    @ExceptionHandler(ArtinfoException.class)
    public ResponseEntity<ErrorResponse> artinfoException(ArtinfoException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
          .code(String.valueOf(statusCode))
          .message(e.getMessage())
          .build();

      return ResponseEntity.status(statusCode)
          .body(body);
    }
}
