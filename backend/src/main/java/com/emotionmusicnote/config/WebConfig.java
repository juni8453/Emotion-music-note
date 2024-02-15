package com.emotionmusicnote.config;

import com.emotionmusicnote.common.LoginInterceptor;
import com.emotionmusicnote.history.domain.LogHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final LogHistoryRepository logHistoryRepository;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginInterceptor(logHistoryRepository))
        .order(1)
        .addPathPatterns("/api/**")
        .excludePathPatterns("/", "/api/check-server-state", "/api/login/**", "/css/**", "/*.ico",
            "/error");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowCredentials(true)
        .allowedOrigins("http://localhost:3000", "http://3.38.153.192", "https://musicdiary.life")
        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
        .maxAge(3600);
  }
}
