package com.emotionmusicnote.note.controller.request;

import lombok.Getter;

@Getter
public class NotePageRequest {

  private static final int MAX_SIZE = 2000;

  private final Integer page;
  private final Integer size;

  public NotePageRequest(Integer page, Integer size) {
    this.page = (page != null) ? page : 1;
    this.size = (size != null) ? size : 5;
  }

  // page 가 0인 경우 최소한 1을 반환하기 위함
  // size 가 너무 커지지 않도록 조치
  public long getOffset() {
    return (long) (Math.max(1, page) - 1) * Math.min(MAX_SIZE, size);
  }
}
