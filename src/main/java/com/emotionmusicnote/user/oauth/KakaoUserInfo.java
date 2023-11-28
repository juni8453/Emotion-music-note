package com.emotionmusicnote.user.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoUserInfo {

  private Long id;

  @JsonProperty("kakao_account")
  private KakaoAccount kakaoAccount;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true) // 필요없는 정보는 제외하도록 @JsonIgnoreProperties
  static class KakaoAccount {
    private KakaoProfile profile;
  }

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class KakaoProfile {
    private String nickname;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    @JsonProperty("thumbnail_image_url")
    private String thumbnailImageUrl;
  }

  public String getNickname() {
    return kakaoAccount.profile.nickname;
  }

  public OAuthProvider getOAuthProvider() {
    return OAuthProvider.KAKAO;
  }
}
