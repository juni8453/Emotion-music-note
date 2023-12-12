package com.emotionmusicnote.spotify.service;

import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Component
public class CreateSpotifyToken {

  private final String clientId = "clientId";

  private final String clientSecret = "clientSecret";

  private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
      .setClientId(clientId)
      .setClientSecret(clientSecret)
      .build();

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
