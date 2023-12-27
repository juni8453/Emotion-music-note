package com.emotionmusicnote.user.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // 필요없는 정보는 제외하도록 @JsonIgnoreProperties
public class KakaoAccount {

  @JsonProperty("profile")
  private KakaoProfile kakaoProfile;
}
