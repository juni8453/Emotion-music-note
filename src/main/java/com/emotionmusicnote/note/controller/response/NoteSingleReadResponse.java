package com.emotionmusicnote.note.controller.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteSingleReadResponse {

  private final Long id;
  private final String emotion;
  private final String content;
  private final LocalDateTime createAt;
  private final LocalDateTime modifiedAt;
  private final NoteWriterResponse noteWriterResponse;

  @Builder
  public NoteSingleReadResponse(Long id, String emotion, String content, LocalDateTime createAt,
      LocalDateTime modifiedAt, NoteWriterResponse noteWriterResponse) {
    this.id = id;
    this.emotion = emotion;
    this.content = content;
    this.createAt = createAt;
    this.modifiedAt = modifiedAt;
    this.noteWriterResponse = noteWriterResponse;
  }
}
