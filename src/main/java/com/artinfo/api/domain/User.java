package com.artinfo.api.domain;

import com.artinfo.api.domain.enums.AuthenticationType;
import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.request.CompanyCertificationCreate;
import com.artinfo.api.request.UserEdit;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "public_nickname")
  private String publicNickname;

  @Column(name = "secret_nickname")
  private String secretNickname;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "icon_image_url", nullable = true)
  private String iconImageUrl;

  @Column(name = "auth_type")
  private AuthenticationType authType;

  @Column(name = "article_cnt")
  private Integer articleCnt = 0;

  @Column(name = "comment_cnt")
  private Integer commentCnt = 0;

  @Column(name = "is_teacher")
  private boolean isTeacher = false;

  @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
  private Lesson lesson;

  @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
  private List<CompanyCertification> companyCertifications;

  @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
  private List<Degree> degrees;

  @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
  private List<Feed> feeds;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public User(String name, String publicNickname, String email, String password, AuthenticationType authType) {
    this.name = name;
    this.publicNickname = publicNickname;
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

  public void editFromUerEdit(UserEdit userEdit) {
    this.name = userEdit.getName();
    this.publicNickname = userEdit.getPublicNickname();
    this.secretNickname = userEdit.getSecretNickname();
    this.iconImageUrl = userEdit.getIconImageUrl();
  }

  public void editFromCompanyCertificationCreate(CompanyCertificationCreate create) {
    this.name = create.getName();
    this.secretNickname = create.getSecretNickname();
  }
}
