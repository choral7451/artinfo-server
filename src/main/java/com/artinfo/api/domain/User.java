package com.artinfo.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "profiles")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "icon_image_url")
  private String iconImageUrl;

  @Column(name = "article_cnt", columnDefinition = "smalint")
  private short articleCnt = 0;

  @Column(name = "comment_cnt", columnDefinition = "smalint")
  private short commentCnt = 0;

  @Column(name = "is_teacher")
  private boolean isTeacher = false;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
  }
}
