package com.artinfo.api.service;

import com.artinfo.api.domain.User;
import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.exception.UserNotFound;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  public UserResponse get(UUID id) {
    User user = userRepository.findById(id)
      .orElseThrow(UserNotFound::new);

    return UserResponse.builder()
      .id(user.getId())
      .name(user.getName())
      .email(user.getEmail())
      .iconImageUrl(user.getIconImageUrl())
      .commentCnt(user.getCommentCnt())
      .articleCnt(user.getArticleCnt())
      .lessonId(Optional.ofNullable(user.getLesson()).map(Lesson::getId).orElse(null))
      .isTeacher(user.isTeacher())
      .build();
  }
}
