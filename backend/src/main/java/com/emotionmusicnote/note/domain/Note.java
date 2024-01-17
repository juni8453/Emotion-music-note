package com.emotionmusicnote.note.domain;

import com.emotionmusicnote.common.BaseTime;
import com.emotionmusicnote.song.domain.Song;
import com.emotionmusicnote.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Note extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String emotion;

  @Lob
  @Column(nullable = false, length = 800)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "note", cascade = CascadeType.REMOVE)
  List<Song> songs = new ArrayList<>();

  @Builder
  public Note(String emotion, String content) {
    this.emotion = emotion;
    this.content = content;
  }

  public void addUser(User createUser) {
    this.user = createUser;
  }

  public void updateNote(String emotion, String content) {
    this.emotion = emotion;
    this.content = content;
  }
}
