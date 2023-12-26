package com.emotionmusicnote.note.controller.response;

import com.emotionmusicnote.user.oauth.OAuthProvider;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteWriterResponse {

  private final String nickname;
  private final String profileImageUrl;
  private final OAuthProvider provider;

  @Builder
  public NoteWriterResponse(String nickname, String profileImageUrl, OAuthProvider provider) {
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
    this.provider = provider;
  }
}
