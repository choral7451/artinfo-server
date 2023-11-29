package com.artinfo.api.response.artist;

import com.artinfo.api.domain.Artist;
import lombok.Getter;

@Getter
public class ArtistDetailResponse {
  private final Long id;
  private final String koreanName;
  private final String englishName;
  private final String mainImageUrl;

  public ArtistDetailResponse(Artist artist) {
    this.id = artist.getId();
    this.koreanName = artist.getKoreanName();
    this.englishName = artist.getEnglishName();
    this.mainImageUrl = artist.getMainImageUrl();
  }
}
