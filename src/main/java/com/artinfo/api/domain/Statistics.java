package com.artinfo.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "statistics")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Statistics {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "number_of_visitors")
  private Long numberOfVisitors;

  @Column(name = "measurement_date", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime measurementDate = LocalDateTime.now().toLocalDate().atStartOfDay();

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  public void increaseVisitors(Long prevVisitors) {
    this.numberOfVisitors = prevVisitors + 1L;
  }

  @Builder
  public Statistics(Long numberOfVisitors) {
    this.numberOfVisitors = numberOfVisitors;
  }
}
