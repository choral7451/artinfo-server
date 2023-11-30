package com.artinfo.api.service;

import com.artinfo.api.domain.Feed;
import com.artinfo.api.exception.FeedNotFound;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.request.feed.FeedSearch;
import com.artinfo.api.response.feed.FeedDetailResponse;
import com.artinfo.api.response.feed.FeedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {

  private final FeedRepository feedRepository;

  public FeedDetailResponse get(Long feedId) {
    Feed feed = feedRepository.findById(feedId)
      .orElseThrow(FeedNotFound::new);

    return FeedDetailResponse.fromFeed(feed);
  }

  public List<FeedResponse> getList(FeedSearch feedSearch) {
    return feedRepository.getList(feedSearch).stream()
      .map(FeedResponse::feed)
      .collect(Collectors.toList());
  }
}
