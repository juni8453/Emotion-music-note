package com.emotionmusicnote.user.domain;

import com.emotionmusicnote.user.oauth.OAuthProvider;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
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

  private String providerId;

  private String profileImageUrl;

  @Enumerated(value = EnumType.STRING)
  private OAuthProvider oAuthProvider;

  @Builder
  public User(String nickname, String providerId, String profileImageUrl, OAuthProvider oAuthProvider) {
    this.nickname = nickname;
    this.providerId = providerId;
    this.profileImageUrl = profileImageUrl;
    this.oAuthProvider = oAuthProvider;
  }
}
