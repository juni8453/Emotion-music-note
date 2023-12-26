package com.emotionmusicnote.note.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<Note, Long>, NoteCustomRepository {

  @Query("SELECT note FROM Note note JOIN FETCH note.user WHERE note.id =:noteId AND note.user.id =:userId")
  Optional<Note> findById(
      @Param("noteId") Long noteId,
      @Param("userId") Long userId);
}
