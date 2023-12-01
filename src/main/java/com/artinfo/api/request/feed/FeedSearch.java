package com.artinfo.api.request.feed;

import jakarta.validation.constraints.NotBlank;
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
  @NotBlank(message = "아티스트 ID를 입력해 주세요.")
  private Long artistId;
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
