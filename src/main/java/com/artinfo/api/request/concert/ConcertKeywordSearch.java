package com.artinfo.api.request.concert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConcertKeywordSearch {
  private Integer size;

  public ConcertKeywordSearch() {
    this.size = 5;
  }
}
