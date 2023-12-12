package com.emotionmusicnote.note.service;

import com.emotionmusicnote.common.PageRequest;
import com.emotionmusicnote.common.exception.NotFoundNoteException;
import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.controller.request.NoteUpdateRequest;
import com.emotionmusicnote.note.controller.response.NoteMultiReadResponse;
import com.emotionmusicnote.note.controller.response.NoteSingleReadResponse;
import com.emotionmusicnote.note.controller.response.NoteWriterResponse;
import com.emotionmusicnote.note.domain.Note;
import com.emotionmusicnote.note.domain.NoteRepository;
import com.emotionmusicnote.spotify.controller.response.SongSavedInNoteResponse;
import com.emotionmusicnote.spotify.domain.Song;
import com.emotionmusicnote.spotify.domain.SongRepository;
import com.emotionmusicnote.user.domain.User;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NoteService {

  private final NoteRepository noteRepository;
  private final SongRepository songRepository;

  @Transactional
  public Long save(NoteSaveRequest request, HttpSession session) {
    User loginUser = (User) session.getAttribute("user");
    String emotion = request.getEmotion();
    String content = request.getContent();

    Note note = request.toEntity(emotion, content);

    Note saveNote = noteRepository.save(note);
    saveNote.addUser(loginUser);

    return saveNote.getId();
  }

  @Transactional
  public void update(Long noteId, NoteUpdateRequest request, HttpSession session) {
    User loginUser = (User) session.getAttribute("user");
    Long loginUserId = loginUser.getId();

    Note findNote = noteRepository.findById(noteId, loginUserId)
        .orElseThrow(NotFoundNoteException::new);

    String emotion = request.getEmotion();
    String content = request.getContent();

    findNote.updateNote(emotion, content);
  }

  @Transactional
  public void delete(Long noteId, HttpSession session) {
    User loginUser = (User) session.getAttribute("user");
    Long loginUserId = loginUser.getId();

    Note findNote = noteRepository.findById(noteId, loginUserId)
        .orElseThrow(NotFoundNoteException::new);

    noteRepository.delete(findNote);
  }

  @Transactional(readOnly = true)
  public NoteSingleReadResponse read(Long noteId, HttpSession session) {
    User loginUser = (User) session.getAttribute("user");
    Long loginUserId = loginUser.getId();

    Note findNote = noteRepository.findById(noteId, loginUserId)
        .orElseThrow(NotFoundNoteException::new);

    NoteWriterResponse noteWriterResponse = createNoteWriterResponse(findNote.getUser());
    SongSavedInNoteResponse songSavedInNoteResponse = createSongSavedInNoteResponse(noteId);

    return NoteSingleReadResponse.builder()
        .id(findNote.getId())
        .emotion(findNote.getEmotion())
        .content(findNote.getContent())
        .createAt(findNote.getCreatedDate())
        .modifiedAt(findNote.getModifiedDate())
        .noteWriterResponse(noteWriterResponse)
        .songSavedInNoteResponse(songSavedInNoteResponse)
        .build();
  }

  private NoteWriterResponse createNoteWriterResponse(User findUser) {
    return NoteWriterResponse.builder()
        .nickname(findUser.getNickname())
        .profileImageUrl(findUser.getProfileImageUrl())
        .provider(findUser.getOAuthProvider())
        .build();
  }

  private SongSavedInNoteResponse createSongSavedInNoteResponse(Long noteId) {
    Optional<Song> findSongByNoteId = songRepository.findByNoteId(noteId);

    if (findSongByNoteId.isPresent()) {
      Song findSong = findSongByNoteId.get();

      return SongSavedInNoteResponse.builder()
          .id(findSong.getId())
          .artistName(findSong.getArtistName())
          .title(findSong.getTitle())
          .albumName(findSong.getAlbumName())
          .imageUrl(findSong.getImageUrl())
          .build();
    }

    return SongSavedInNoteResponse.builder().build(); // 노래가 없는 경우에 대한 기본 값 처리
  }


  @Transactional(readOnly = true)
  public NoteMultiReadResponse readAll(PageRequest pageRequest, HttpSession session) {
    User loginUser = (User) session.getAttribute("user");
    Long loginUserId = loginUser.getId();

    List<Note> notes = noteRepository.findAll(loginUserId, pageRequest);

    List<NoteSingleReadResponse> response = notes.stream()
        .map(note -> NoteSingleReadResponse.builder()
            .id(note.getId())
            .emotion(note.getEmotion())
            .content(note.getContent())
            .createAt(note.getCreatedDate())
            .modifiedAt(note.getModifiedDate())
            .noteWriterResponse(
                NoteWriterResponse.builder()
                    .nickname(note.getUser().getNickname())
                    .provider(note.getUser().getOAuthProvider())
                    .profileImageUrl(note.getUser().getProfileImageUrl())
                    .build())
            .build()).collect(Collectors.toList());

    return NoteMultiReadResponse.builder().notes(response).build();
  }

}
