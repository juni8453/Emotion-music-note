package com.emotionmusicnote.song.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SongMultiSearchResponse {

  private final List<SongSingleSearchResponse> responses;

  @Builder
  public SongMultiSearchResponse(
      List<SongSingleSearchResponse> responses) {
    this.responses = responses;
  }
}
