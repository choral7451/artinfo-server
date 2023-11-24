package com.artinfo.api.request.lesson;

import com.artinfo.api.exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class LessonCreate {
  //todo 추후 삭제 필요
  private UUID userId;

  @NotBlank(message = "이름을 입력해 주세요.")
  private String name;

  @NotBlank(message = "이미지 주소를 입력해 주세요.")
  private String imageUrl;

  private List<String> locations;

  private List<String> majors;

  @NotBlank(message = "연락처를 입력해 주세요.")
  private String phone;

  private Integer fee;

  @NotBlank(message = "소개를 입력해 주세요.")
  private String intro;

  private List<Map<String, String>> degrees;

  public void validate() {
    if (userId == null) {
      throw new InvalidRequest("userId", "유저 ID를 입력해 주세요.");
    }
    if (locations == null) {
      throw new InvalidRequest("locations", "레슨 가능 지역을 입력해 주세요.");
    }
    if (majors == null) {
      throw new InvalidRequest("majors", "전공을 입력해 주세요.");
    }
    if (fee == null) {
      throw new InvalidRequest("fee", "레슨비를 입력해 주세요.");
    }
    if (degrees == null) {
      throw new InvalidRequest("degrees", "학위를 입력해 주세요.");
    }
  }
}
