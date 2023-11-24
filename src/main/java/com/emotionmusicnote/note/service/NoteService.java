package com.emotionmusicnote.note.service;

import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.controller.response.NoteSingleReadResponse;
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
  public Long save(NoteSaveRequest request) {
    String emotion = request.getEmotion();
    String content = request.getContent();

    Note note = request.toEntity(emotion, content);

    Note saveNote = noteRepository.save(note);

    return saveNote.getId();
  }

  @Transactional(readOnly = true)
  public NoteSingleReadResponse read(Long id) {
    Note findNote = noteRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 노트 입니다."));

    return NoteSingleReadResponse.builder()
        .id(findNote.getId())
        .emotion(findNote.getEmotion())
        .content(findNote.getContent())
        .build();
  }
}
