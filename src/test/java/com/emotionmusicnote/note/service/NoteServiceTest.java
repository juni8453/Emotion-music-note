package com.emotionmusicnote.note.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.emotionmusicnote.common.exception.NotFoundNoteException;
import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.controller.response.NoteSingleReadResponse;
import com.emotionmusicnote.note.domain.NoteRepository;
import com.emotionmusicnote.user.domain.User;
import com.emotionmusicnote.user.domain.UserRepository;
import com.emotionmusicnote.user.oauth.OAuthProvider;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
class NoteServiceTest {

  @Autowired
  private NoteService noteService;

  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  private UserRepository userRepository;

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
    noteRepository.deleteAll();
  }

  @Test
  @DisplayName("현재 로그인한 유저는 노트를 저장할 수 있습니다.")
  void 노트_저장() {
    // given
    String emotion = "슬픔";
    String content = "내용 테스트";

    NoteSaveRequest request = NoteSaveRequest.builder()
        .emotion(emotion)
        .content(content)
        .build();

    // when
    noteService.save(request, session);
  }

  @Test
  @DisplayName("노트 아이디와 현재 로그인한 유저를 통해 해당하는 노트를 단일 조회합니다.")
  void 내_노트_단일_조회() {
    // given
    User loginUser = (User) session.getAttribute("user");
    String emotion = "슬픔";
    String content = "내용 테스트";

    NoteSaveRequest request = NoteSaveRequest.builder()
        .emotion(emotion)
        .content(content)
        .build();

    Long saveNoteId = noteService.save(request, session);

    // when
    NoteSingleReadResponse response = noteService.read(saveNoteId, session);

    // then
    assertThat(response.getEmotion()).isEqualTo(emotion);
    assertThat(response.getContent()).isEqualTo(content);
    assertThat(response.getCreateAt()).isBefore(LocalDateTime.now());
    assertThat(response.getModifiedAt()).isBefore(LocalDateTime.now());
    assertThat(response.getNoteWriterResponse().getNickname()).isEqualTo(loginUser.getNickname());
  }

  @Test
  @DisplayName("노트 아이디가 맞지 않는 경우 NotFoundNoteException 이 호출됩니다.")
  void 내_노트_단일_조회_실패() {
    // given
    String emotion = "슬픔";
    String content = "내용 테스트";

    NoteSaveRequest request = NoteSaveRequest.builder()
        .emotion(emotion)
        .content(content)
        .build();

    Long saveNoteId = noteService.save(request, session);

    // when & then
    assertThrows(NotFoundNoteException.class,
        () -> noteService.read(saveNoteId + 1L, session));
  }

}
