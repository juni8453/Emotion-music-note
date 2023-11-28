package com.emotionmusicnote.user.domain;

import com.emotionmusicnote.user.oauth.OAuthProvider;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nickname;

  private OAuthProvider oAuthProvider;

  public User(String nickname, OAuthProvider oAuthProvider) {
    this.nickname = nickname;
    this.oAuthProvider = oAuthProvider;
  }
}
