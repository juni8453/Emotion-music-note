package com.emotionmusicnote.note.domain;

import com.emotionmusicnote.common.PageRequest;
import java.time.LocalDate;
import java.util.List;

public interface NoteCustomRepository {

  List<Note> findAll(Long loginUserId, PageRequest pageRequest);

  List<Note> findAllByDate(Long loginUserId, PageRequest pageRequest, LocalDate startDate,
      LocalDate endDate);
}
