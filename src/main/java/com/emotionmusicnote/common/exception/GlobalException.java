package com.emotionmusicnote.common.exception;

public abstract class GlobalException extends RuntimeException {

  public GlobalException(String message) {
    super(message);
  }

  public abstract int getStatusCode();
}
