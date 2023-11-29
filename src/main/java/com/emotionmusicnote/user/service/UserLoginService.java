package com.emotionmusicnote.user.service;

import com.emotionmusicnote.user.domain.UserRepository;
import com.emotionmusicnote.user.oauth.KakaoTokens;
import com.emotionmusicnote.user.oauth.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class UserLoginService {

  private final UserRepository userRepository;
  private final RestTemplate restTemplate;

  /**
   * GET/POST https://kapi.kakao.com/v2/user/me 으로 설정한 Headers 정보를 담아 요청
   * 사용자 정보를 가져오기 위한 Required
   * Parameter
   * Header
   *  Authorization : Bearer ${ACCESS_TOKEN}
   *  Content-type : application/x-www-form-urlencoded;charset=utf-8
   */
  public KakaoTokens getToken(String code) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", "fe5038ec11bee454a846309f8fb8d1ad");
    body.add("redirect_uri", "http://localhost:8080/oauth/kakao");
    body.add("code", code);

    HttpEntity<MultiValueMap<String, String>> requestToken = new HttpEntity<>(body, headers);

    KakaoTokens responseKakaoTokens = restTemplate.postForEntity(
            "https://kauth.kakao.com/oauth/token",
            requestToken,
            KakaoTokens.class)
        .getBody();

    assert responseKakaoTokens != null;

    return responseKakaoTokens;
  }

  /**
   * GET/POST https://kapi.kakao.com/v2/user/me 으로 설정한 Headers 정보를 담아 요청
   * 사용자 정보를 가져오기 위한 Required
   * Parameter Header
   *  Authorization : Bearer ${ACCESS_TOKEN}
   *  Content-type : application/x-www-form-urlencoded;charset=utf-8
   */
  public KakaoUserInfo getUserInfo(KakaoTokens kakaoTokens) {
    HttpHeaders userInfoHeaders = new HttpHeaders();
    userInfoHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    userInfoHeaders.add("Authorization", "Bearer " + kakaoTokens.getAccessToken());

    HttpEntity<MultiValueMap<String, String>> requestUserProfile = new HttpEntity<>(userInfoHeaders);

    return restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            requestUserProfile,
            KakaoUserInfo.class)
        .getBody();
  }
}
