package com.artinfo.api.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Table(name = "lessons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "profile_id")
  private UUID userId;

  @Column(name = "image_url")
  private String imageUrl;

  @JsonManagedReference
  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(
    name = "lessons_locations",
    joinColumns = @JoinColumn(name = "lesson_id"),
    inverseJoinColumns = @JoinColumn(name = "location_id")
  )
  private Set<Location> locations;

  @Column(name = "name")
  private String name;

  @JsonManagedReference
  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(
    name = "lessons_majors",
    joinColumns = @JoinColumn(name = "lesson_id"),
    inverseJoinColumns = @JoinColumn(name = "major_id")
  )
  private Set<Major> majors;

  @Column(name = "phone")
  private String phone;

  @Column(name = "fee")
  private Integer fee;

  @Column(name = "intro")
  private String intro;

  @OneToMany(mappedBy = "lesson",cascade = CascadeType.REMOVE)
  private List<Degree> degrees;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp with time zone not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Lesson(UUID userId, String imageUrl, Set<Location> locations, String name, Set<Major> majors, String phone, Integer fee, String intro, List<Degree> degrees) {
    this.userId = userId;
    this.imageUrl = imageUrl;
    this.locations = locations;
    this.name = name;
    this.majors = majors;
    this.phone = phone;
    this.fee = fee;
    this.intro = intro;
    this.degrees = degrees;
  }
}
