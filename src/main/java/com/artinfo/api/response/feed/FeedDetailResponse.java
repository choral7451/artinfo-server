package com.artinfo.api.response.feed;

import com.artinfo.api.domain.Feed;
import com.artinfo.api.domain.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class FeedDetailResponse {
  private final Long feedId;
  private final String authorName;
  private final String authorIconImageUrl;
  private final String title;
  private final String contents;
  private final List<String> imageUrls;
  private final Integer countOfLikes;
  private final Integer countOfComments;
  private final LocalDateTime createdAt;

  public static FeedDetailResponse fromFeed(Feed feed) {
    return FeedDetailResponse.builder()
      .feedId(feed.getId())
      .authorName(feed.getUser().getName())
      .authorIconImageUrl(feed.getUser().getIconImageUrl())
      .title(feed.getTitle())
      .contents(feed.getContents())
      .imageUrls(feed.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
      .countOfLikes(feed.getCountOfLikes())
      .countOfComments(feed.getCountOfComments())
      .createdAt(feed.getCreatedAt())
      .build();
  }
}
