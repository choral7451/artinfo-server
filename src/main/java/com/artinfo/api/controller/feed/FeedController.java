package com.artinfo.api.controller.feed;

import com.artinfo.api.request.feed.FeedSearch;
import com.artinfo.api.response.feed.FeedDetailResponse;
import com.artinfo.api.response.feed.FeedResponse;
import com.artinfo.api.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeedController {

  private final FeedService feedService;

  @GetMapping("/feeds/{feedId}")
  public FeedDetailResponse get(@PathVariable(name = "feedId") Long feedId) {
    return feedService.get(feedId);
  }

  @GetMapping("/feeds")
  public List<FeedResponse> getList(@ModelAttribute FeedSearch feedSearch) {
    return feedService.getList(feedSearch);
  }
}
