package com.artinfo.api.request.artist;

import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
public class ArtistSearch {

  private static final int MAX_SIZE = 2000;

  private Integer page = 1;
  private Integer size = 20;

  public ArtistSearch() {
    this.page = 1;
    this.size = 5;
  }

  public long getOffset() {
    return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
  }
}
