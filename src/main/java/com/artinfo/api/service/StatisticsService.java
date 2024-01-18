package com.artinfo.api.service;

import com.artinfo.api.domain.Statistics;
import com.artinfo.api.repository.statistics.StatisticsRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.response.statistics.StatisticsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

  private final StatisticsRepository statisticsRepository;
  private final UserRepository userRepository;
  public void increaseVisitor() {
    LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();

    Optional<Statistics> todayStatics = statisticsRepository.findByMeasurementDate(today);
    if (todayStatics.isEmpty()) {
      Statistics statistics = Statistics.builder().numberOfVisitors(1L).build();
      statisticsRepository.save(statistics);
    } else {
      Long visitors = todayStatics.get().getNumberOfVisitors();
      todayStatics.get().increaseVisitors(visitors);

      statisticsRepository.save(todayStatics.get());
    }
  }

  public StatisticsResponse get() {
    LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();

    Optional<Statistics> todayStatics = statisticsRepository.findByMeasurementDate(today);
    Statistics statistics;
    if (todayStatics.isEmpty()) {
      statistics = Statistics.builder().numberOfVisitors(0L).build();
      statisticsRepository.save(statistics);
    } else {
      statistics = todayStatics.get();
    }

    Long users = userRepository.count();

    return new StatisticsResponse(statistics.getNumberOfVisitors(), users);
  }

}
