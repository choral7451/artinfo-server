package com.artinfo.api.service;

import com.artinfo.api.domain.CompanyCertification;
import com.artinfo.api.domain.Image;
import com.artinfo.api.domain.User;
import com.artinfo.api.domain.enums.AuthenticationType;
import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.exception.UserNotFound;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.user.CompanyCertificationRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.CompanyCertificationCreate;
import com.artinfo.api.request.UserEdit;
import com.artinfo.api.response.MeResponse;
import com.artinfo.api.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final CompanyCertificationRepository companyCertificationRepository;
  private final ImageRepository imageRepository;

  public UserResponse get(UUID id) {
    User user = userRepository.findById(id)
      .orElseThrow(UserNotFound::new);

    return UserResponse.builder()
      .id(user.getId())
      .name(user.getPublicNickname())
      .email(user.getEmail())
      .iconImageUrl(user.getIconImageUrl())
      .commentCnt(user.getCommentCnt())
      .articleCnt(user.getArticleCnt())
      .lessonId(Optional.ofNullable(user.getLesson()).map(Lesson::getId).orElse(null))
      .isTeacher(user.isTeacher())
      .build();
  }

  public MeResponse getMe(UUID id) {
    User user = userRepository.findById(id)
      .orElseThrow(UserNotFound::new);




    MeResponse.MeResponseBuilder meResponseBuilder = MeResponse.builder()
      .id(user.getId())
      .name(user.getName())
      .publicNickname(user.getPublicNickname())
      .secretNickname(user.getSecretNickname())
      .email(user.getEmail())
      .iconImageUrl(user.getIconImageUrl())
      .lessonId(Optional.ofNullable(user.getLesson()).map(Lesson::getId).orElse(null))
      .isTeacher(user.isTeacher());

    Optional<CompanyCertification> certification = companyCertificationRepository.findByUserIdAndIsVerified(user.getId(), true);

    if (certification.isPresent()) {
      meResponseBuilder.companyName(certification.get().getName());
      meResponseBuilder.companyCategory(certification.get().getCategory());
    }

    return meResponseBuilder.build();
  }

  @Transactional
  public void edit(UserEdit edit) {
    User user = userRepository.findById(edit.getUserId())
      .orElseThrow(UserNotFound::new);

    user.editFromUerEdit(edit);
  }

  @Transactional
  public void createCompanyCertification(CompanyCertificationCreate create) {
    User user = userRepository.findById(create.getUserId())
      .orElseThrow(UserNotFound::new);

    CompanyCertification certification = CompanyCertification.builder()
      .name(create.getCompanyName())
      .user(user)
      .build();

    companyCertificationRepository.save(certification);

    List<Image> images = create.getImageUrls().stream().map(imageUrl -> {
      return Image.builder()
        .url(imageUrl)
        .certification(certification)
        .build();
    }).toList();

    imageRepository.saveAll(images);

    user.editFromCompanyCertificationCreate(create);
  }



  public void editAuthenticationType(String email, String oauth2ClientName) {
    User user = userRepository.findByEmail(email)
      .orElseThrow(UserNotFound::new);

    AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());

    user.editAuthType(authType);
  }
}
