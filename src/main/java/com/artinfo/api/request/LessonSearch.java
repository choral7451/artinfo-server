package com.artinfo.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
@Builder
public class LessonSearch {

  private static final int MAX_SIZE = 2000;

  @Builder.Default
  private Integer page = 1;

  @Builder.Default
  private Integer size = 20;

  private List<String> location;

  private List<String> subject;

  public long getOffset() {
    return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
  }
}
