package com.emotionmusicnote.user.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInUserInfo {
  private final String nickname;
  private final String profileImageUrl;
  private final String jSessionId;

  @Builder
  public SignInUserInfo(String nickname, String profileImageUrl, String jSessionId) {
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
    this.jSessionId = jSessionId;
  }
}
