package com.artinfo.api.response.artist;

import com.artinfo.api.domain.Artist;
import lombok.Getter;

@Getter
public class ArtistResponse {
  private final Long id;
  private final String  name;
  private final String mainImageUrl;

  public ArtistResponse(Artist artist) {
    this.id = artist.getId();
    this.name = artist.getName();
    this.mainImageUrl = artist.getMainImageUrl();
  }
}
