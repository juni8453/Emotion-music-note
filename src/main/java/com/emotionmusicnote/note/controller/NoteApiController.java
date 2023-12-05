package com.emotionmusicnote.note.controller;

import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.controller.response.NoteSingleReadResponse;
import com.emotionmusicnote.note.service.NoteService;
import jakarta.servlet.http.HttpSession;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NoteApiController {

  private final NoteService noteService;

  @PostMapping("/api/notes")
  public ResponseEntity<Long> save(
      @RequestBody NoteSaveRequest request, HttpSession session) {

    Long saveNoteId = noteService.save(request, session);

    URI location = URI.create("api/notes/" + saveNoteId);

    return ResponseEntity.created(location).body(saveNoteId);
  }

  @GetMapping("/api/notes/{id}")
  public ResponseEntity<NoteSingleReadResponse> read(
      @PathVariable Long id, HttpSession session) {

    NoteSingleReadResponse response = noteService.read(id, session);

    return ResponseEntity.ok(response);
  }
}
