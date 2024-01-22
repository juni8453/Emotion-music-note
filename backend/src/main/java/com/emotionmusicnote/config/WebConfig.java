package com.emotionmusicnote.config;

import com.emotionmusicnote.common.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginInterceptor())
        .order(1)
        .addPathPatterns("/api/**")
        .excludePathPatterns("/", "/css/**", "/*.ico", "/error");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowCredentials(true)
        .allowedOrigins("http://localhost:8081, http://3.38.153.192")
        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
        .maxAge(3600);
  }
}
