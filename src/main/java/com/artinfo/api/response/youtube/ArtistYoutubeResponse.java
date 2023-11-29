package com.artinfo.api.response.youtube;

import com.artinfo.api.domain.Youtube;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArtistYoutubeResponse {
  private final Long id;
  private final String artistName;
  private final String title;
  private final String linkUrl;
  private final LocalDateTime publishedAt;

  public ArtistYoutubeResponse(Youtube youtube) {
    this.id = youtube.getId();
    this.artistName = youtube.getArtist().getKoreanName();
    this.title = youtube.getTitle();
    this.linkUrl = youtube.getLinkUrl();
    this.publishedAt = youtube.getPublishedAt();
  }
}
