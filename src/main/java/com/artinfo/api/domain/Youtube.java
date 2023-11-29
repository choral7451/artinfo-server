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
@Table(name = "youtubes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Youtube {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "link_url", columnDefinition = "text")
  private String linkUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "artist_id", nullable = true)
  private Artist artist;

  @Column(name = "published_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime publishedAt;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Youtube(String title, String linkUrl, LocalDateTime publishedAt, Artist artist) {
    this.title = title;
    this.linkUrl = linkUrl;
    this.publishedAt = publishedAt;
    this.artist = artist;
  }
}
