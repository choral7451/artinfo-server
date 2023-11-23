package com.artinfo.api.domain;

import com.artinfo.api.domain.lesson.Lesson;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "degrees")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Degree {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "degree")
  private String degree;

  @Column(name = "campus_name")
  private String campusName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id", nullable = false)
  private Lesson lesson;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false)
  private User user;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Degree(String degree, String campusName, Lesson lesson, User user) {
    this.degree = degree;
    this.campusName = campusName;
    this.lesson = lesson;
    this.user = user;
  }
}
