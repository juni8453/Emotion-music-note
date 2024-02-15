package com.emotionmusicnote.history.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class LogHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private String nickname;

  private String httpMethod;

  private String requestUri;

  private int responseStatus;

  private LocalDateTime callTime;

  @Builder
  public LogHistory(Long id, Long userId, String nickname, String httpMethod,
      String requestUri, int responseStatus, LocalDateTime callTime) {
    this.id = id;
    this.userId = userId;
    this.nickname = nickname;
    this.httpMethod = httpMethod;
    this.requestUri = requestUri;
    this.responseStatus = responseStatus;
    this.callTime = callTime;
  }
}
