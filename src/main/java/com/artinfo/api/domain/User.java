package com.artinfo.api.domain;

import com.artinfo.api.domain.enums.AuthenticationType;
import com.artinfo.api.domain.lesson.Lesson;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Column(name = "auth_type")
  private AuthenticationType authType;

  @Column(name = "article_cnt", columnDefinition = "smalint")
  private short articleCnt = 0;

  @Column(name = "comment_cnt", columnDefinition = "smalint")
  private short commentCnt = 0;

  @Column(name = "is_teacher")
  private boolean isTeacher = false;

  @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
  private Lesson lesson;

  @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
  private List<Degree> degrees;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public User(String name, String email, String password, AuthenticationType authType) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.authType = authType;
  }

  public void editIsTeacher(Boolean isTeacher) {
    this.isTeacher = isTeacher;
  }
  public void editAuthType(AuthenticationType authType) {
    this.authType = authType;
  }
}
