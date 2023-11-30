package com.artinfo.api.service;

import com.artinfo.api.domain.Feed;
import com.artinfo.api.exception.FeedNotFound;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.response.feed.FeedDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final FeedRepository feedRepository;

  public FeedDetailResponse get(Long feedId) {
    Feed feed = feedRepository.findById(feedId)
      .orElseThrow(FeedNotFound::new);

    return FeedDetailResponse.fromFeed(feed);
  }
}
