package com.emotionmusicnote.user.service;

import com.emotionmusicnote.user.domain.User;
import com.emotionmusicnote.user.domain.UserRepository;
import com.emotionmusicnote.user.oauth.KakaoTokens;
import com.emotionmusicnote.user.oauth.KakaoUserInfo;
import com.emotionmusicnote.user.oauth.OAuthProvider;
import jakarta.servlet.http.HttpSession;
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

  public KakaoTokens login(String code, HttpSession session) {
    KakaoTokens kakaoTokens = getToken(code);
    KakaoUserInfo kakaoUserInfo = getUserInfo(kakaoTokens);
    User user = findOrCreateUser(kakaoUserInfo);

    // 사용자의 고유 정보를 포함해 세션 저장
    // TODO : JSESSIONID 를 쿠키로 넘길 때 최소한 HttpOnly 정도는 필요할 듯
    //  JSESSIONID 는 어떻게 검증 되는지 알아봐야한다. (톰캣 세션 매니저 ?)
    //  id, nickname 이 저장되는 의미가 있는건지 ? (세션을 가지고 올 때 사용할 것 같기도 한데)
    //  로그아웃 구현하면서 좀 알아보자.
    session.setAttribute("id", user.getId());
    session.setAttribute("nickname", user.getNickname());

    return kakaoTokens;
  }

  private User findOrCreateUser(KakaoUserInfo kakaoUserInfo) {
    // 네이버 고유 식별 ID 는 문자열 타입이라 String 으로 통일을 위해 String.valueOf() 사용
    String providerId = String.valueOf(kakaoUserInfo.getId());

    // providerId 를 통해 User 를 찾고 있다면 해당 유저를 반환, 없다면 생성 후 반환
    return userRepository.findByProviderId(providerId)
        .orElseGet(() -> saveUser(kakaoUserInfo));
  }

  private User saveUser(KakaoUserInfo kakaoUserInfo) {
    User user = User.builder()
        .nickname(kakaoUserInfo.getNickname())
        .providerId(String.valueOf(kakaoUserInfo.getId()))
        .oAuthProvider(OAuthProvider.KAKAO)
        .build();

    return userRepository.save(user);
  }

  /**
   * GET/POST https://kapi.kakao.com/v2/user/me 으로 설정한 Headers 정보를 담아 요청
   * 사용자 정보를 가져오기 위한 Required
   * Parameter Header
   * Authorization : Bearer ${ACCESS_TOKEN}
   * Content-type : application/x-www-form-urlencoded;charset=utf-8
   */
  private KakaoTokens getToken(String code) {
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
   * GET/POST https://kapi.kakao.com/v2/user/me 으로 설정한 Headers 정보를 담아 요청 사용자 정보를 가져오기 위한 Required
   * Parameter Header Authorization : Bearer ${ACCESS_TOKEN} Content-type :
   * application/x-www-form-urlencoded;charset=utf-8
   */
  private KakaoUserInfo getUserInfo(KakaoTokens kakaoTokens) {
    HttpHeaders userInfoHeaders = new HttpHeaders();
    userInfoHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    userInfoHeaders.add("Authorization", "Bearer " + kakaoTokens.getAccessToken());

    HttpEntity<MultiValueMap<String, String>> requestUserProfile = new HttpEntity<>(
        userInfoHeaders);

    return restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            requestUserProfile,
            KakaoUserInfo.class)
        .getBody();
  }
}
