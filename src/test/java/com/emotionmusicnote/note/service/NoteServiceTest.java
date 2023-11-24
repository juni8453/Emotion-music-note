package com.emotionmusicnote.note.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.domain.Note;
import com.emotionmusicnote.note.domain.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteServiceTest {

  @Autowired
  private NoteService noteService;

  @Autowired
  private NoteRepository noteRepository;

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

    // then
    Note findNote = noteRepository.findById(1L).get();
    System.out.println("emotion = " + findNote.getEmotion());
    System.out.println("content = " + findNote.getContent());

    assertThat(findNote.getId()).isEqualTo(1L);
  }
}