package com.emotionmusicnote.note.domain;

import com.emotionmusicnote.note.controller.request.NotePageRequest;
import java.util.List;

public interface NoteCustomRepository {

  List<Note> findAll(Long loginUserId, NotePageRequest notePageRequest);

}
