package com.emotionmusicnote.note.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteMultiReadResponse {

  private final List<NoteSingleReadResponse> notes;

  @Builder
  public NoteMultiReadResponse(List<NoteSingleReadResponse> notes) {
    this.notes = notes;
  }
}
