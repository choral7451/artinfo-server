package com.artinfo.api.domain.lesson;

import com.artinfo.api.domain.Degree;
import com.artinfo.api.domain.Location;
import com.artinfo.api.domain.Major;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class LessonEditor {
  //todo 추후 삭제 필요
  private String imageUrl;
  private List<Location> locations;
  private String name;
  private List<Major> majors;
  private String phone;
  private Integer fee;
  private String intro;
  private List<Degree> degrees;

  @Builder
  public LessonEditor(String imageUrl, List<Location> locations, String name, List<Major> majors, String phone, Integer fee, String intro, List<Degree> degrees) {
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
