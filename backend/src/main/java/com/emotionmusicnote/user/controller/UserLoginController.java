package com.emotionmusicnote.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

  // 로그인 폼
  @GetMapping("/login")
  public String loginForm() {
    return "login";
  }
}
