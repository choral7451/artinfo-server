package com.artinfo.api.domain.job;

import com.artinfo.api.domain.Major;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class JobEditor {
  //todo 추후 삭제 필요
  private final String title;
  private final String companyName;
  private final String companyImageUrl;
  private final String linkUrl;
  private final String contents;
  private final List<Major> majors;

  @Builder
  public JobEditor(String title, String companyName, String companyImageUrl,String linkUrl, String contents, List<Major> majors) {
    this.title = title;
    this.companyName = companyName;
    this.companyImageUrl = companyImageUrl;
    this.linkUrl = linkUrl;
    this.contents = contents;
    this.majors = majors;
  }
}
