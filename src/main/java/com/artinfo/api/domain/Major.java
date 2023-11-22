package com.artinfo.api.domain;

import com.artinfo.api.domain.lesson.Lesson;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "majors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @JsonBackReference
  @ManyToMany(mappedBy = "majors", cascade = CascadeType.REMOVE)
  private List<Lesson> lessons;

  @Builder
  public Major(String name) {
    this.name = name;
  }
}
