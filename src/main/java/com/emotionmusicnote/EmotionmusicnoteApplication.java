package com.emotionmusicnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EmotionmusicnoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmotionmusicnoteApplication.class, args);
	}

}
