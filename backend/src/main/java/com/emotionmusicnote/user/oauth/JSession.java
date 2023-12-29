package com.emotionmusicnote.user.oauth;

import lombok.Getter;

@Getter
public class JSession {
  private final String jSessionId;

  public JSession(String jSessionId) {
    this.jSessionId = jSessionId;
  }
}
