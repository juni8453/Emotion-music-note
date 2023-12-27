package com.emotionmusicnote.song.controller;

import com.emotionmusicnote.common.PageRequest;
import com.emotionmusicnote.song.controller.request.SongSaveRequest;
import com.emotionmusicnote.song.controller.response.SongMultiSearchResponse;
import com.emotionmusicnote.song.service.SongService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SongApiController {

  private final SongService songService;

  @GetMapping("/api/songs")
  public ResponseEntity<SongMultiSearchResponse> searchSongs(
      @RequestParam String keyword, @ModelAttribute PageRequest pageRequest) {

    SongMultiSearchResponse response = songService.searchTracks(keyword, pageRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/api/songs/{noteId}")
  public ResponseEntity<Long> saveSong(
      @RequestBody SongSaveRequest request,
      @PathVariable Long noteId) {
    Long saveSongId = songService.saveSongMyNote(noteId, request);

    URI location = URI.create("api/songs/" + saveSongId);

    return ResponseEntity.created(location).body(saveSongId);
  }
}
