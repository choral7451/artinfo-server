package com.artinfo.api.domain;

import com.artinfo.api.domain.concert.Concert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "artists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "korean_name")
  private String koreanName;

  @Column(name = "english_name")
  private String englishName;

  @Column(name = "main_image_url", columnDefinition = "text")
  private String mainImageUrl;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @OneToMany(mappedBy = "artist")
  private List<Concert> concerts;

  @OneToMany(mappedBy = "artist")
  private List<Youtube> youtubes;

  @Builder
  public Artist(String koreanName, String englishName, String mainImageUrl) {
    this.koreanName = koreanName;
    this.englishName = englishName;
    this.mainImageUrl = mainImageUrl;
  }

}
