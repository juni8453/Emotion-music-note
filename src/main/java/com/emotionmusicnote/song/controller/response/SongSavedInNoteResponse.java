package com.emotionmusicnote.song.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SongSavedInNoteResponse {

  private final Long id;
  private final String artistName;
  private final String title;
  private final String albumName;
  private final String imageUrl;

  @Builder
  public SongSavedInNoteResponse(Long id, String artistName, String title, String albumName,
      String imageUrl) {
    this.id = (id != null) ? id : 0L;
    this.artistName = (artistName != null) ? artistName : "Unknown Artist";
    this.title = (title != null) ? title : "Unknown Title";
    this.albumName = (albumName != null) ? albumName : "Unknown albumName";
    this.imageUrl = (imageUrl != null) ? imageUrl : "Unknown ImageUrl";
  }
}
