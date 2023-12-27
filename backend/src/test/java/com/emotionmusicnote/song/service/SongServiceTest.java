package com.emotionmusicnote.song.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.emotionmusicnote.common.PageRequest;
import com.emotionmusicnote.note.domain.Note;
import com.emotionmusicnote.note.domain.NoteRepository;
import com.emotionmusicnote.song.controller.request.SongSaveRequest;
import com.emotionmusicnote.song.controller.response.SongMultiSearchResponse;
import com.emotionmusicnote.song.domain.SongRepository;
import com.emotionmusicnote.user.domain.User;
import com.emotionmusicnote.user.domain.UserRepository;
import com.emotionmusicnote.user.oauth.OAuthProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
class SongServiceTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private SongService songService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SongRepository songRepository;

  @Autowired
  private NoteRepository noteRepository;

  private final MockHttpSession session = new MockHttpSession();

  @Order(value = 1)
  @BeforeEach
  void saveDummyData() {
    User user = User.builder()
        .nickname("dummy_nickname")
        .providerId("dummy_providerId")
        .profileImageUrl("dummy_profileImageUrl")
        .oAuthProvider(OAuthProvider.KAKAO)
        .build();

    User saveUser = userRepository.save(user);
    session.setAttribute("user", saveUser);
  }

  @Order(value = 2)
  @BeforeEach
  public void db_init() {
    songRepository.deleteAll();
  }

  @Test
  @DisplayName("노트 저장 후 선택사항으로 노래를 저장할 수 있습니다.")
  void 노래_저장() {
    // given
    String emotion = "슬픔";
    String content = "내용 테스트";

    Note note = Note.builder()
        .emotion(emotion)
        .content(content)
        .build();

    Note saveNote = noteRepository.save(note);

    SongSaveRequest request = SongSaveRequest.builder()
        .artistName("song artist name")
        .title("song title")
        .albumName("song album name")
        .imageUrl("song image url")
        .build();

    // when
    Long saveSongId = songService.saveSongMyNote(saveNote.getId(), request);

    // then
    assertThat(saveSongId).isEqualTo(1L);
  }

  @Test
  @DisplayName("Spotify Search API 를 활용해 노래를 검색할 수 있습니다.")
  void 노래_검색() {

    // given
    String keyword = " ";
    PageRequest pageRequest = new PageRequest(1, 5);

    // when
    SongMultiSearchResponse songMultiSearchResponse = songService.searchTracks(keyword,
        pageRequest);

    // then
    System.out.println(songMultiSearchResponse.toString());

  }
}
