package com.emotionmusicnote.note.controller.request;

import com.emotionmusicnote.note.domain.Note;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NoteSaveRequest {

  @Size(max = 50, message = "감정은 50자를 넘길 수 없습니다.")
  @NotBlank(message = "오늘의 감정을 적어주세요.")
  private String emotion;

  @Size(max = 800, message = "내용은 2000자를 넘길 수 없습니다.")
  @NotBlank(message = "일기 내용을 적어주세요.")
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
