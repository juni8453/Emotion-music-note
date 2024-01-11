package com.emotionmusicnote.user.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInUserInfo {
  private final String nickname;
  private final String profileImageUrl;
  private final String sessionId;

  @Builder
  public SignInUserInfo(String nickname, String profileImageUrl, String sessionId) {
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
    this.sessionId = sessionId;
  }
}
