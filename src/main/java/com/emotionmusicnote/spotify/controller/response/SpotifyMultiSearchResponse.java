package com.emotionmusicnote.spotify.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SpotifyMultiSearchResponse {

  private final List<SpotifySingleSearchResponse> responses;

  @Builder
  public SpotifyMultiSearchResponse(
      List<SpotifySingleSearchResponse> responses) {
    this.responses = responses;
  }
}
