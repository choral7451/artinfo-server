package com.artinfo.api.repository.statistics;

import com.artinfo.api.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

  Optional<Statistics> findByMeasurementDate(LocalDateTime localDateTime);
}
