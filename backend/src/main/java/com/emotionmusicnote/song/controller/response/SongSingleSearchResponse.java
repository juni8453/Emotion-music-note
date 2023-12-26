package com.emotionmusicnote.song.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SongSingleSearchResponse {

  private final String artistName;
  private final String title;
  private final String albumName;
  private final String imageUrl;

  @Builder
  public SongSingleSearchResponse(String artistName, String title, String albumName,
      String imageUrl) {
    this.artistName = artistName;
    this.title = title;
    this.albumName = albumName;
    this.imageUrl = imageUrl;
  }
}
