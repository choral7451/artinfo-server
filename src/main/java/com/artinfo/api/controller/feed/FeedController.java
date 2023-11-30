package com.artinfo.api.controller.feed;

import com.artinfo.api.response.feed.FeedDetailResponse;
import com.artinfo.api.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedController {

  private final FeedService feedService;

  @GetMapping("/feeds/{feedId}")
  public FeedDetailResponse get(@PathVariable(name = "feedId") Long feedId) {
    return feedService.get(feedId);
  }
}
