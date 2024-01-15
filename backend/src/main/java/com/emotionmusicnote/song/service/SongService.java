package com.emotionmusicnote.song.service;

import com.emotionmusicnote.common.PageRequest;
import com.emotionmusicnote.common.exception.NotFoundNoteException;
import com.emotionmusicnote.note.domain.Note;
import com.emotionmusicnote.note.domain.NoteRepository;
import com.emotionmusicnote.song.controller.request.SongSaveRequest;
import com.emotionmusicnote.song.controller.request.SongUpdateRequest;
import com.emotionmusicnote.song.controller.response.SongMultiSearchResponse;
import com.emotionmusicnote.song.controller.response.SongSingleSearchResponse;
import com.emotionmusicnote.song.domain.Song;
import com.emotionmusicnote.song.domain.SongRepository;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@RequiredArgsConstructor
@Service
public class SongService {

  private final CreateSpotifyToken createSpotifyToken;
  private final NoteRepository noteRepository;
  private final SongRepository songRepository;

  @Transactional(readOnly = true)
  public SongMultiSearchResponse searchTracks(String keyword, PageRequest pageRequest) {
    createSpotifyToken.setAccessToken();
    SpotifyApi spotifyApi = createSpotifyToken.getSpotifyApi();

    List<SongSingleSearchResponse> response = new ArrayList<>();

    try {
      SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(keyword)
          .q(keyword)
          .limit(pageRequest.getSize())
          .offset((int) pageRequest.getOffset())
          .build();

      Paging<Track> searchResult = searchTracksRequest.execute();
      Track[] tracks = searchResult.getItems();

      for (Track track : tracks) {
        String title = track.getName();
        AlbumSimplified album = track.getAlbum();
        String albumName = album.getName();
        ArtistSimplified[] artists = album.getArtists();
        String artistName = artists[0].getName();
        Image[] images = album.getImages();
        String imageUrl = (images.length > 0) ? images[0].getUrl() : "NO_IMAGE";

        response.add(SongSingleSearchResponse.builder()
            .artistName(artistName)
            .title(title)
            .albumName(albumName)
            .imageUrl(imageUrl)
            .build());
      }

    } catch (IOException | SpotifyWebApiException | ParseException exception) {
      exception.printStackTrace();
    }

    return SongMultiSearchResponse.builder()
        .responses(response)
        .build();
  }

  @Transactional
  public Long saveSongMyNote(Long noteId, SongSaveRequest request) {
    Note findNote = noteRepository.findById(noteId)
        .orElseThrow(NotFoundNoteException::new);

    String artistName = request.getArtistName();
    String albumName = request.getAlbumName();
    String title = request.getTitle();
    String imageUrl = request.getImageUrl();

    Song song = request.toEntity(artistName, title, albumName, imageUrl);

    Song saveSong = songRepository.save(song);
    saveSong.addNote(findNote);

    return saveSong.getId();
  }

  @Transactional
  public void updateSongMyNote(Long noteId, SongUpdateRequest request) {
    Song findSong = songRepository.findByNoteId(noteId)
        .orElseThrow(NotFoundNoteException::new);

    String artistName = request.getArtistName();
    String albumName = request.getAlbumName();
    String title = request.getTitle();
    String imageUrl = request.getImageUrl();

    findSong.updateSong(artistName, albumName, title, imageUrl);
  }
}
