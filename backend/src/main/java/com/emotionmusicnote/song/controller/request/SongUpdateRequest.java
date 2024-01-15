package com.emotionmusicnote.song.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SongUpdateRequest {

  private String artistName;
  private String title;
  private String albumName;
  private String imageUrl;

  public SongUpdateRequest(String artistName, String title, String albumName,
      String imageUrl) {
    this.artistName = artistName;
    this.title = title;
    this.albumName = albumName;
    this.imageUrl = imageUrl;
  }
}
