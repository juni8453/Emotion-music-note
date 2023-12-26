package com.emotionmusicnote.song.domain;

import com.emotionmusicnote.note.domain.Note;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Song {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String artistName;

  private String title;

  private String albumName;

  private String imageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_id")
  private Note note;

  @Builder
  public Song(String artistName, String title, String albumName, String imageUrl) {
    this.artistName = artistName;
    this.title = title;
    this.albumName = albumName;
    this.imageUrl = imageUrl;
  }

  public void addNote(Note note) {
    this.note = note;
  }
}
