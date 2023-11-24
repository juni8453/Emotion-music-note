package com.emotionmusicnote.note.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.controller.response.NoteSingleReadResponse;
import com.emotionmusicnote.note.domain.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteServiceTest {

  @Autowired
  private NoteService noteService;

  @Autowired
  private NoteRepository noteRepository;

  @BeforeEach
  public void db_init() {
    noteRepository.deleteAll();
  }

  @Test
  void saveTest() {
    // given
    String emotion = "슬픔";
    String content = "내용 테스트";

    NoteSaveRequest request = NoteSaveRequest.builder()
        .emotion(emotion)
        .content(content)
        .build();

    // when
    noteService.save(request);
  }

  @Test
  void read() {
    // given
    String emotion = "슬픔";
    String content = "내용 테스트";

    NoteSaveRequest request = NoteSaveRequest.builder()
        .emotion(emotion)
        .content(content)
        .build();

    Long saveNoteId = noteService.save(request);

    // when
    NoteSingleReadResponse response = noteService.read(saveNoteId);

    // then
    assertThat(response.getEmotion()).isEqualTo(emotion);
    assertThat(response.getContent()).isEqualTo(content);
  }
}