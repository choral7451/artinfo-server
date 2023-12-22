package com.artinfo.api.request.concert;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ConcertCreate {
  private UUID userId;

  private List<Long> artistIds;

  private String location;

  @NotBlank(message = "제목을 입력해 주세요.")
  private String title;

  @NotBlank(message = "내용을 입력해 주세요.")
  private String contents;

  private String posterUrl;

  private String linkUrl;

  private Boolean isActive;

  private LocalDateTime performanceTime;
}
