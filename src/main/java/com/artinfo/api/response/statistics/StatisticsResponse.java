package com.artinfo.api.response.statistics;

import lombok.Getter;

@Getter
public class StatisticsResponse {
  private final Long visitors;
  private final Long users;

  public StatisticsResponse(Long visitors, Long users) {
    this.visitors = visitors;
    this.users = users;
  }
}
