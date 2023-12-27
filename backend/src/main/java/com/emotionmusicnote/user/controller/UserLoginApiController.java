package com.emotionmusicnote.user.controller;

import com.emotionmusicnote.user.oauth.KakaoTokens;
import com.emotionmusicnote.user.service.UserLoginService;
import jakarta.servlet.http.HttpSession;
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
  // 클라이언트에 저장할 수 있도록 토큰 전달
  @GetMapping("/oauth/kakao")
  public ResponseEntity<KakaoTokens> oauthLogin(
      @RequestParam String code, HttpSession session) {
    KakaoTokens responseKakaoTokens = userLoginService.login(code, session);

    return ResponseEntity.ok(responseKakaoTokens);
  }
}
