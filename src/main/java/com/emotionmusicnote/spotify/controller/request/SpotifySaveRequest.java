package com.emotionmusicnote.spotify.controller.request;

import com.emotionmusicnote.spotify.domain.Song;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SpotifySaveRequest {

  private String artistName;
  private String title;
  private String albumName;
  private String imageUrl;

  @Builder
  public SpotifySaveRequest(String artistName, String title, String albumName,
      String imageUrl) {
    this.artistName = artistName;
    this.title = title;
    this.albumName = albumName;
    this.imageUrl = imageUrl;
  }

  public Song toEntity(String artistName, String title, String albumName, String imageUrl) {
    return Song.builder()
        .artistName(artistName)
        .albumName(albumName)
        .title(title)
        .imageUrl(imageUrl)
        .build();
  }
}
