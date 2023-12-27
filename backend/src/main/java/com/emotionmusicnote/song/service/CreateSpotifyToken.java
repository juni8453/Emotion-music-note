package com.emotionmusicnote.song.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@RequiredArgsConstructor
@Component
public class CreateSpotifyToken {

  private final SpotifyApi spotifyApi;

  public void setAccessToken() {
    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

    try {
      ClientCredentials clientCredentials = clientCredentialsRequest.execute();
      spotifyApi.setAccessToken(clientCredentials.getAccessToken());

    } catch (IOException | SpotifyWebApiException | ParseException exception) {
      exception.printStackTrace();
    }
  }

  public SpotifyApi getSpotifyApi() {
    return spotifyApi;
  }
}
