package com.emotionmusicnote.spotify.controller;

import com.emotionmusicnote.common.PageRequest;
import com.emotionmusicnote.spotify.controller.request.SpotifySaveRequest;
import com.emotionmusicnote.spotify.controller.response.SpotifyMultiSearchResponse;
import com.emotionmusicnote.spotify.service.SpotifyService;
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
public class SpotifyApiController {

  private final SpotifyService spotifyService;

  @GetMapping("/api/songs")
  public ResponseEntity<SpotifyMultiSearchResponse> searchSongs(
      @RequestParam String keyword, @ModelAttribute PageRequest pageRequest) {

    SpotifyMultiSearchResponse response = spotifyService.searchTracks(keyword, pageRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/api/songs/{noteId}")
  public ResponseEntity<Long> saveSong(
      @RequestBody SpotifySaveRequest request,
      @PathVariable Long noteId) {
    Long saveSongId = spotifyService.saveSongMyNote(noteId, request);

    URI location = URI.create("api/songs/" + saveSongId);

    return ResponseEntity.created(location).body(saveSongId);
  }
}
