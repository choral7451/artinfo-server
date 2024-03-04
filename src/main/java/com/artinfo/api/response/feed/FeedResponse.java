package com.artinfo.api.response.feed;

import com.artinfo.api.domain.enums.FeedCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class FeedResponse {
  private final Long feedId;
  private final UUID authorId;
  private final String authorName;
  private final String authorIconImageUrl;
  private final String title;
  private final String contents;
  private final FeedCategory category;
  private final List<String> imageUrls;
  private final Integer countOfLikes;
  private final Integer countOfComments;
  private final Boolean isLiking;
  private final LocalDateTime createdAt;
}
