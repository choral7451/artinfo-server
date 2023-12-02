package com.artinfo.api.response.concert;

import com.artinfo.api.domain.Concert;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConcertResponse {
  private final Long id;
  private final String posterUrl;
  private final String title;
  private final String location;
  private final LocalDateTime performanceTime;

  public ConcertResponse(Concert concert) {
    this.id = concert.getId();
    this.posterUrl = concert.getPosterUrl();
    this.title = concert.getTitle();
    this.location = concert.getLocation();
    this.performanceTime = concert.getPerformanceTime();
  }
}
