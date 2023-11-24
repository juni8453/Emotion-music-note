package com.emotionmusicnote.note.controller;

import com.emotionmusicnote.note.controller.request.NoteSaveRequest;
import com.emotionmusicnote.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NoteApiController {

  private final NoteService noteService;

  @PostMapping("/api/notes")
  public void save(
      @RequestBody NoteSaveRequest request) {

    noteService.save(request);
  }
}
