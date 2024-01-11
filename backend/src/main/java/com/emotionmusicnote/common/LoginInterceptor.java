package com.emotionmusicnote.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (request.getMethod().equals("OPTIONS")) {
      System.out.println("Preflight Request");
      return true;
    }

    HttpSession session = request.getSession(false);

    if (session == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 세션입니다.");

      return false;
    }

    return true;
  }
}
