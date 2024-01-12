package com.artinfo.api.request.job;

import com.artinfo.api.exception.InvalidRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JobSearch {

  private Integer page = 1;
  private Integer size = 20;
  private String sortBy;
  private List<String> major;
  private Sort.Direction direction;

  public JobSearch() {
    this.page = 1;
    this.size = 10;
    this.sortBy = "createdAt";
    this.direction = Sort.Direction.DESC;
  }


  public void validate() {
    if (page <= 0) {
      throw new InvalidRequest("page", "페이지 번호는 1 이상으로 입력해 주세요.");
    }
  }

  public Pageable toPageable() {
    return PageRequest.of(page - 1 , size, Sort.by(direction, sortBy));
  }
}
