package com.artinfo.api.response.artist;

import com.artinfo.api.domain.Artist;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArtistDetailResponse {
  private final Long id;
  private final String koreanName;
  private final String englishName;
  private final String mainImageUrl;

  public static ArtistDetailResponse fromArtist(Artist artist) {
    return ArtistDetailResponse.builder()
      .id(artist.getId())
      .koreanName(artist.getKoreanName())
      .englishName(artist.getEnglishName())
      .mainImageUrl(artist.getMainImageUrl())
      .build();
  }
}
