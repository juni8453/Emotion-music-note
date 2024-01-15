package com.emotionmusicnote.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundSongException extends GlobalException {

  private static final String MESSAGE = "찾을 수 없는 노래입니다.";

  public NotFoundSongException() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.NOT_FOUND.value();
  }
}
