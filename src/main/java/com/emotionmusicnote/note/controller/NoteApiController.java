package com.emotionmusicnote.note.controller;

import com.emotionmusicnote.common.PageRequest;
import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.controller.request.NoteUpdateRequest;
import com.emotionmusicnote.note.controller.response.NoteMultiReadResponse;
import com.emotionmusicnote.note.controller.response.NoteSingleReadResponse;
import com.emotionmusicnote.note.service.NoteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NoteApiController {

  private final NoteService noteService;

  @PostMapping("/api/notes")
  public ResponseEntity<Long> save(
      @RequestBody @Valid NoteSaveRequest request, HttpSession session) {

    Long saveNoteId = noteService.save(request, session);

    URI location = URI.create("api/notes/" + saveNoteId);

    return ResponseEntity.created(location).body(saveNoteId);
  }

  @PutMapping("/api/notes/{id}")
  public void update(
      @PathVariable Long id,
      @RequestBody @Valid NoteUpdateRequest request, HttpSession session) {

    noteService.update(id, request, session);
  }

  @DeleteMapping("/api/notes/{id}")
  public void delete(
      @PathVariable Long id, HttpSession session) {

    noteService.delete(id, session);
  }

  @GetMapping("/api/notes/{id}")
  public ResponseEntity<NoteSingleReadResponse> read(
      @PathVariable Long id, HttpSession session) {

    NoteSingleReadResponse response = noteService.read(id, session);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/api/notes")
  public ResponseEntity<NoteMultiReadResponse> readAll(
      @ModelAttribute PageRequest pageRequest, HttpSession session) {

    NoteMultiReadResponse response = noteService.readAll(pageRequest, session);

    return ResponseEntity.ok(response);
  }

}
