package com.artinfo.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "lessons")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "profile_id")
  private UUID profileId;

  @Column(name = "image_url")
  private String imageUrl;

  @ColumnTransformer(write = "?::jsonb")
  @Column(name = "locations", columnDefinition = "jsonb")
  private String locations;

  @Column(name = "name")
  private String name;

  @ColumnTransformer(write = "?::jsonb")
  @Column(name = "subjects", columnDefinition = "jsonb")
  private String subjects;

  @Column(name = "phone")
  private String phone;

  @Column(name = "fee")
  private Long fee;

  @Column(name = "intro")
  private String intro;

  @ColumnTransformer(write = "?::jsonb")
  @Column(name = "degree", columnDefinition = "jsonb")
  private String degree;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Lesson(UUID profileId, String imageUrl, String locations, String name, String subjects, String phone, Long fee, String intro, String degree, LocalDateTime createdAt) {
    this.profileId = profileId;
    this.imageUrl = imageUrl;
    this.locations = locations;
    this.name = name;
    this.subjects = subjects;
    this.phone = phone;
    this.fee = fee;
    this.intro = intro;
    this.degree = degree;
  }
}
