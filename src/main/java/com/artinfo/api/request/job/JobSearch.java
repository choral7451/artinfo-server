package com.artinfo.api.request.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
@AllArgsConstructor
public class JobSearch {

  private static final int MAX_SIZE = 2000;

  private Integer page;
  private Integer size;
  private List<String> major;
  private Sort.Direction direction;

  public JobSearch() {
    this.page = 1;
    this.size = 10;
  }

  public long getOffset() {
    return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
  }
}
