package com.artinfo.api.request.feed;

import com.artinfo.api.domain.enums.FeedCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
public class FeedSearch {

  private static final int MAX_SIZE = 2000;

  private UUID requestUserId;
  private Long artistId;
  private FeedCategory category;
  private Integer page = 1;
  private Integer size = 20;

  public FeedSearch() {
    this.page = 1;
    this.size = 5;
  }

  public long getOffset() {
    return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
  }
}
