package com.emotionmusicnote.user.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

  private String nickname;

  @JsonProperty("profile_image_url")
  private String profileImageUrl;

  @JsonProperty("thumbnail_image_url")
  private String thumbnailImageUrl;
}
