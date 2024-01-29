package com.artinfo.api.domain.lesson;

import com.artinfo.api.domain.Degree;
import com.artinfo.api.domain.Location;
import com.artinfo.api.domain.Major;
import com.artinfo.api.domain.User;
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
@Table(name = "lessons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "fee")
  private Integer fee;

  @Column(name = "intro")

  private String intro;

  @OneToMany(mappedBy = "lesson",cascade = CascadeType.REMOVE)
  private List<Major> majors;

  @OneToMany(mappedBy = "lesson",cascade = CascadeType.REMOVE)
  private List<Location> locations;

  @OneToMany(mappedBy = "lesson",cascade = CascadeType.REMOVE)
  private List<Degree> degrees;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false)
  private User user;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Lesson(User user, String imageUrl, String name, String phone, Integer fee, String intro, List<Degree> degrees) {
    this.user = user;
    this.imageUrl = imageUrl;
    this.name = name;
    this.phone = phone;
    this.fee = fee;
    this.intro = intro;
    this.degrees = degrees;
  }

  public void edit(LessonEditor editor) {
    this.imageUrl = editor.getImageUrl();
    this.name = editor.getName();
    this.phone = editor.getPhone();
    this.fee = editor.getFee();
    this.intro = editor.getIntro();
    this.degrees = editor.getDegrees();
  }
}
