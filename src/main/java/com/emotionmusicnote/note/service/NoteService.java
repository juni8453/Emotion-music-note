package com.emotionmusicnote.note.service;

import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.domain.Note;
import com.emotionmusicnote.note.domain.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NoteService {

  private final NoteRepository noteRepository;

  @Transactional
  public void save(NoteSaveRequest request) {
    String emotion = request.getEmotion();
    String content = request.getContent();

    Note note = request.toEntity(emotion, content);

    noteRepository.save(note);
  }
}
