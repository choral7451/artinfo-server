package com.artinfo.api.service;

import com.artinfo.api.domain.*;
import com.artinfo.api.exception.UserNotFound;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.repository.lesson.LocationRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.DegreeRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.LessonCreate;
import com.artinfo.api.request.LessonSearch;
import com.artinfo.api.response.lesson.LessonDetailResponse;
import com.artinfo.api.response.lesson.LessonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

  private final LessonRepository lessonRepository;
  private final UserRepository userRepository;
  private final LocationRepository locationRepository;
  private final MajorRepository majorRepository;
  private final DegreeRepository degreeRepository;

  public LessonDetailResponse get(Long id) {
    Lesson lesson = lessonRepository.findById(id)
      .orElseThrow(UserNotFound::new);

    return LessonDetailResponse.builder()
      .id(lesson.getId())
      .userId(lesson.getUserId())
      .imageUrl(lesson.getImageUrl())
      .locations(lesson.getLocations().stream().map(Location::getName)
        .collect(Collectors.toList()))
      .name(lesson.getName())
      .majors(lesson.getMajors().stream().map(Major::getName)
        .collect(Collectors.toList())
      )
      .phone(lesson.getPhone())
      .fee(lesson.getFee())
      .intro(lesson.getIntro())
      .degrees(lesson.getDegrees().stream()
        .collect(Collectors.groupingBy(
          Degree::getDegree,
          Collectors.mapping(Degree::getCampusName, Collectors.toList())
        ))
      ).build();
  }

  public List<LessonResponse> getList(LessonSearch lessonSearch) {
    return lessonRepository.getList(lessonSearch).stream()
      .map(LessonResponse::new)
      .collect(Collectors.toList());
  }

  @Transactional
  public void create(UUID userId, LessonCreate lessonCreate) {
//    User user = userRepository.findById(userId)
//      .orElseThrow(UserNotFound::new);

    Set<Location> locations = new HashSet<>();
    for(String location: lessonCreate.getLocations()) {
      Optional<Location> fetchedLocation = locationRepository.findByName(location);
      if(fetchedLocation.isPresent()) {
        locations.add(fetchedLocation.get());
      } else {
        Location CreatedLocation = Location.builder().name(location).build();
        locationRepository.save(CreatedLocation);
        locations.add(CreatedLocation);
      }
    }

    Set<Major> majors = new HashSet<>();
    for(String major: lessonCreate.getMajors()) {
      Optional<Major> fetchedMajor = majorRepository.findByName(major);
      if(fetchedMajor.isPresent()) {
        majors.add(fetchedMajor.get());
      } else {
        Major CreatedMajor = Major.builder().name(major).build();
        majorRepository.save(CreatedMajor);
        majors.add(CreatedMajor);
      }
    }

    Lesson lesson = Lesson.builder()
      .userId(userId)
      .locations(locations)
      .majors(majors)
      .imageUrl(lessonCreate.getImageUrl())
      .name(lessonCreate.getName())
      .phone(lessonCreate.getPhone())
      .fee(lessonCreate.getFee())
      .intro(lessonCreate.getIntro())
      .build();

    lessonRepository.save(lesson);

    List<Degree> degrees = lessonCreate.getDegrees().entrySet().stream()
      .flatMap(entry -> entry.getValue().stream()
        .map(campusName -> Degree.builder()
          .degree(entry.getKey())
          .campusName(campusName)
          .lesson(lesson)
          .build()))
      .collect(Collectors.toList());

    degreeRepository.saveAll(degrees);
  }
}