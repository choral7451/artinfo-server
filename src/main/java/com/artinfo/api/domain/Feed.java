package com.artinfo.api.domain;

import com.artinfo.api.domain.enums.FeedCategory;
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
@Table(name = "feeds")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "content", columnDefinition = "text")
  private String contents;

  @Column(name = "count_of_views")
  private Integer countOfViews = 0;

  @Column(name = "count_of_likes")
  private Integer countOfLikes = 0;

  @Column(name = "count_of_comments")
  private Integer countOfComments = 0;

  @Column(name = "deleted")
  private Boolean isDeleted = false;

  @Column(name = "is_ad")
  private Boolean isAd = false;

  @Column(name = "category")
  @Enumerated(EnumType.STRING)
  private FeedCategory category;
  
  @OneToMany(mappedBy = "feed")
  private List<Image> images;

  @OneToMany(mappedBy = "feed")
  private List<Like> likes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "artist_id")
  private Artist artist;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private User user;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Feed(String title, String contents, FeedCategory category, Artist artist, User user) {
    this.title = title;
    this.contents = contents;
    this.category = category;
    this.artist = artist;
    this.user = user;
  }

  public void delete() {
    this.isDeleted = true;
  }
}
