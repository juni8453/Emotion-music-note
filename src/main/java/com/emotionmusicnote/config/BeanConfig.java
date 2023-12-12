package com.emotionmusicnote.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;

@Configuration
public class BeanConfig {

  @Value("${spotify.clientId}")
  private String clientId;

  @Value("${spotify.clientSecret}")
  private String clientSecret;

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  public SpotifyApi spotifyApi() {
    return new SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .build();
  }

}
