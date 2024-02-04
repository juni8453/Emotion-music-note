package com.emotionmusicnote.note.controller.response;

import com.emotionmusicnote.song.controller.response.SongSavedInNoteResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteSingleReadResponse {

  private final Long id;
  private final String emotion;
  private final String content;
  private final LocalDate createAt;
  private final LocalDate modifiedAt;
  private final NoteWriterResponse noteWriterResponse;
  private final List<SongSavedInNoteResponse> songSavedInNoteResponses;

  @Builder
  public NoteSingleReadResponse(Long id, String emotion, String content, LocalDate createAt,
      LocalDate modifiedAt, NoteWriterResponse noteWriterResponse,
      List<SongSavedInNoteResponse> songSavedInNoteResponses) {
    this.id = id;
    this.emotion = emotion;
    this.content = content;
    this.createAt = createAt;
    this.modifiedAt = modifiedAt;
    this.noteWriterResponse = noteWriterResponse;
    this.songSavedInNoteResponses = songSavedInNoteResponses;
  }
}
