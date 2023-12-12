package com.emotionmusicnote.spotify.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends JpaRepository<Song, Long> {

  @Query("SELECT song FROM Song song JOIN FETCH song.note WHERE song.note.id = :noteId")
  Optional<Song> findByNoteId(@Param("noteId") Long noteId);
}
