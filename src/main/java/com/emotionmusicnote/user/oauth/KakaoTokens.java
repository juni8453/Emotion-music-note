package com.emotionmusicnote.user.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoTokens {

  // 카카오에서 보내주는 파라미터 이름과 동일하도록 @JsonProperty 설정
  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("expires_in")
  private String expiresIn;

  @JsonProperty("refresh_token_expires_in")
  private String refreshTokenExpiresIn;

  @JsonProperty("scope")
  private String scope;
}
