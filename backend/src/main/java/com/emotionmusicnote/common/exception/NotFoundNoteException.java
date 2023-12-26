package com.emotionmusicnote.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundNoteException extends GlobalException {

  private static final String MESSAGE = "찾을 수 없는 노트입니다.";

  public NotFoundNoteException() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.NOT_FOUND.value();
  }
}
