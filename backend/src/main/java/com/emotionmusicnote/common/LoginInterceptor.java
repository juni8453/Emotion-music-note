package com.emotionmusicnote.common;

import com.emotionmusicnote.common.exception.SessionExpiredException;
import com.emotionmusicnote.history.domain.LogHistory;
import com.emotionmusicnote.history.domain.LogHistoryRepository;
import com.emotionmusicnote.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

  private final LogHistoryRepository logHistoryRepository;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {

    if (request.getMethod().equals("OPTIONS")) {
      System.out.println("Preflight Request");
      return true;
    }

    HttpSession session = request.getSession(false);

    if (session == null) {
      throw new SessionExpiredException();
    }

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {

    String httpMethod = request.getMethod();
    String requestUri = request.getRequestURI();
    int responseStatus = response.getStatus();
    User user = (User) request.getSession().getAttribute("user");

    LogHistory logHistory = LogHistory.builder()
        .userId(user.getId())
        .nickname(user.getNickname())
        .httpMethod(httpMethod)
        .requestUri(requestUri)
        .responseStatus(responseStatus)
        .callTime(LocalDateTime.now())
        .build();

    logHistoryRepository.save(logHistory);
  }
}
