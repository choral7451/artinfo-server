package com.artinfo.api.request.concert;

import com.artinfo.api.domain.enums.ConcertCategory;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
public class ConcertSearch {

  private static final int MAX_SIZE = 2000;

  private Integer page;
  private Integer size;
  private ConcertCategory category;
  private String keyword;

  public ConcertSearch() {
    this.page = 1;
    this.size = 5;
  }


  public long getOffset() {
    return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
  }
}
