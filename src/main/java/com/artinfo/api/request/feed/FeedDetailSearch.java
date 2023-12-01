package com.artinfo.api.request.feed;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FeedDetailSearch {
  private UUID requestUserId;
}
