package com.artinfo.api.response.concert;

import com.artinfo.api.domain.concert.Concert;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArtistConcertResponse {
  private final Long id;
  private final String title;
  private final String location;
  private final LocalDateTime performanceTime;
  private final Boolean isActive;

  public ArtistConcertResponse(Concert concert) {
    this.id = concert.getId();
    this.title = concert.getTitle();
    this.location = concert.getLocation();
    this.performanceTime = concert.getPerformanceTime();
    this.isActive = concert.getIsActive();
  }
}
