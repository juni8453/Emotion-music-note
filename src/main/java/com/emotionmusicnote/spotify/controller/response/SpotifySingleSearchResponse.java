package com.emotionmusicnote.spotify.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SpotifySingleSearchResponse {

  private final String artistName;
  private final String title;
  private final String albumName;
  private final String imageUrl;

  @Builder
  public SpotifySingleSearchResponse(String artistName, String title, String albumName,
      String imageUrl) {
    this.artistName = artistName;
    this.title = title;
    this.albumName = albumName;
    this.imageUrl = imageUrl;
  }
}
