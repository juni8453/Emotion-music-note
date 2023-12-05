package com.emotionmusicnote.note.controller.request;

import com.emotionmusicnote.note.domain.Note;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NoteSaveRequest {

  private String emotion;

  private String content;

  @Builder
  public NoteSaveRequest(String emotion, String content) {
    this.emotion = emotion;
    this.content = content;
  }

  public Note toEntity(String emotion, String content) {
    return Note.builder()
        .emotion(emotion)
        .content(content)
        .build();
  }
}
