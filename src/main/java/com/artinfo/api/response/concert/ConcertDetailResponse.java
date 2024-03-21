package com.artinfo.api.response.concert;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ConcertDetailResponse {
  private final Long id;
  private final UUID authorId;
  private final String authorEmail;
  private final String authorPublicNickName;
  private final String authorIconImageUrl;
  private final String title;
  private final String contents;
  private final String location;
  private final String category;
  private final Boolean isActive;
  private final String linkUrl;
  private final String posterUrl;
  private final LocalDateTime performanceTime;
}
