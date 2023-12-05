package com.emotionmusicnote.user.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoUserInfo {

  private Long id;

  @JsonProperty("kakao_account")
  private KakaoAccount kakaoAccount;

  public String getNickname() {
    return kakaoAccount.getKakaoProfile().getNickname();
  }

  public OAuthProvider getOAuthProvider() {
    return OAuthProvider.KAKAO;
  }
}
