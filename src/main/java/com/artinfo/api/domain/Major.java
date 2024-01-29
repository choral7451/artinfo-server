package com.artinfo.api.domain;

import com.artinfo.api.domain.job.Job;
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
@Table(name = "majors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recruit_job_id")
  private Job job;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  private Lesson lesson;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Major(String name, Job job, Lesson lesson) {
    this.name = name;
    this.job = job;
    this.lesson = lesson;
  }
}
