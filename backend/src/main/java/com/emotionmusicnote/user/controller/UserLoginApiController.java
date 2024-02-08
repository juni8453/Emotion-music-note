package com.emotionmusicnote.user.controller;

import com.emotionmusicnote.user.controller.response.SignInUserInfo;
import com.emotionmusicnote.user.service.UserLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserLoginApiController {

  private final UserLoginService userLoginService;

  // 프론트에서 보낸 인가 코드를 통해 카카오 서버에서 code 를 받아 로그인 처리
  @GetMapping("/api/login/{code}")
  public ResponseEntity<SignInUserInfo> login(
      @PathVariable("code") String code, HttpSession session) {
    SignInUserInfo signInUserInfo = userLoginService.login(code, session);

    return ResponseEntity.ok(signInUserInfo);
  }

  @GetMapping("/api/check-server-state")
  public String checkServerState() {
    return "Server is up and running";
  }

  @GetMapping("/api/check-session")
  public String checkSession() {
    return "Session OK";
  }
}
