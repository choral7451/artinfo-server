package com.artinfo.api.domain.concert;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "concert_keywords")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertKeyword {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "keyword")
  private String keyword;

  @Column(name = "fetch_count")
  private Long fetchCount = 1L;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();


  @Builder
  public ConcertKeyword(String keyword) {
    this.keyword = keyword;
  }
  public void increaseFetchCount(Long prevFetchCount) {
    this.fetchCount = prevFetchCount + 1L;
  }
}
