package com.emotionmusicnote.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ExceptionResponse valid(MethodArgumentNotValidException exception) {
    ExceptionResponse response = ExceptionResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message("잘못된 요청입니다.")
        .build();

    for (FieldError fieldError : exception.getFieldErrors()) {
      response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return response;
  }
}
