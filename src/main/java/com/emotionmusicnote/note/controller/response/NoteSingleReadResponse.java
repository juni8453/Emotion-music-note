package com.emotionmusicnote.note.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteSingleReadResponse {

  private final Long id;
  private final String emotion;
  private final String content;

  @Builder
  public NoteSingleReadResponse(Long id, String emotion, String content) {
    this.id = id;
    this.emotion = emotion;
    this.content = content;
  }
}
