package com.artinfo.api.domain;

import com.artinfo.api.domain.enums.ConcertCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "concerts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "contents", columnDefinition = "text")
  private String contents;

  @Column(name = "category")
  private String category;

  @Column(name = "location")
  private String location;

  @Column(name = "poster_url")
  private String posterUrl;

  @Column(name = "link_url", columnDefinition = "text")
  private String linkUrl;

  @Column(name = "count_of_views")
  private Long countOfViews;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "artist_id", nullable = true)
  private Artist artist;

  @Column(name = "performance_time")
  private LocalDateTime performanceTime;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Concert(String title, String contents, ConcertCategory category, String location, String posterUrl, String linkUrl, LocalDateTime performanceTime, Boolean isActive,User user, Artist artist) {
    this.title = title;
    this.contents = contents;
    this.category = category.toString();
    this.location = location;
    this.posterUrl = posterUrl;
    this.linkUrl = linkUrl;
    this.isActive = isActive;
    this.user = user;
    this.artist = artist;
    this.performanceTime = performanceTime;
  }
}
