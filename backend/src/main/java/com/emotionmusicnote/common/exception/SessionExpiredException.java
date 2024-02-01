package com.emotionmusicnote.common.exception;

import org.springframework.http.HttpStatus;

public class SessionExpiredException extends GlobalException {

  private static final String MESSAGE = "로그인이 필요합니다.";

  public SessionExpiredException() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.UNAUTHORIZED.value();
  }
}
