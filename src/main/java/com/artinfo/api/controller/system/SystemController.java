package com.artinfo.api.controller.system;

import com.artinfo.api.response.statistics.StatisticsResponse;
import com.artinfo.api.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemController {

  private final StatisticsService statisticsService;

  @PostMapping("/statistics/visitors")
  public void increaseVisitors() {
    statisticsService.increaseVisitor();
  }

  @GetMapping("/statistics")
  public StatisticsResponse get() {
    return statisticsService.get();
  }
}
