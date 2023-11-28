package com.emotionmusicnote.user.controller;

import com.emotionmusicnote.user.oauth.KakaoTokens;
import com.emotionmusicnote.user.oauth.KakaoUserInfo;
import com.emotionmusicnote.user.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserLoginApiController {

  private final UserLoginService userLoginService;

  // callback URI 을 통해 code 를 받아 token URI 로 token 을 요청한다.
  // Service 단에서 처리하도록 리팩토링 예정
  // 프론트 개발 시작하면서 code 는 프론트에서 전달하는 방식으로 바꿔야한다.
  @GetMapping("/oauth/kakao")
  public ResponseEntity<KakaoUserInfo> oauthLogin(@RequestParam String code) {

    KakaoTokens kakaoTokens = userLoginService.getToken(code);
    KakaoUserInfo kakaoUserInfo = userLoginService.getUserInfo(kakaoTokens);

    return ResponseEntity.ok(kakaoUserInfo);
  }
}
