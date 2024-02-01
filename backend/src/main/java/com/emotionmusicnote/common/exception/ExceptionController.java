package com.emotionmusicnote.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ExceptionResponse typeMismatch() {
    return ExceptionResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message("잘못된 요청입니다.")
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NotFoundNoteException.class)
  public ExceptionResponse notFoundNote(NotFoundNoteException exception) {
    return ExceptionResponse.builder()
        .code(exception.getStatusCode())
        .message(exception.getMessage())
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NotFoundSongException.class)
  public ExceptionResponse notFoundSong(NotFoundSongException exception) {
    return ExceptionResponse.builder()
        .code(exception.getStatusCode())
        .message(exception.getMessage())
        .build();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(SessionExpiredException.class)
  public ExceptionResponse sessionExpired(SessionExpiredException exception) {
    return ExceptionResponse.builder()
        .code(exception.getStatusCode())
        .message(exception.getMessage())
        .build();
  }
}
