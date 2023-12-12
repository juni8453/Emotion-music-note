package com.emotionmusicnote.note.domain;

import com.emotionmusicnote.common.PageRequest;
import java.util.List;

public interface NoteCustomRepository {

  List<Note> findAll(Long loginUserId, PageRequest pageRequest);

}
