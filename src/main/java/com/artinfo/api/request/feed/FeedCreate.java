package com.artinfo.api.request.feed;

import com.artinfo.api.domain.enums.FeedCategory;
import com.artinfo.api.exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class FeedCreate {
  //todo 추후 삭제 필요
  private UUID userId;

  @NotBlank(message = "제목을 입력해 주세요.")
  private String title;

  @NotBlank(message = "내용을 입력해 주세요.")
  private String contents;

  private FeedCategory category;

  private List<String> imageUrls;

  private Long artistId;

  public void validate() {
    if (userId == null) {
      throw new InvalidRequest("userId", "유저 ID를 입력해 주세요.");
    }
    if (artistId == null) {
      throw new InvalidRequest("artistId", "아티스트 ID를 입력해 주세요.");
    }
  }
}
