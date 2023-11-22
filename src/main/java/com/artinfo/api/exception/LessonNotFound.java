package com.artinfo.api.exception;

public class LessonNotFound extends ArtinfoException {
  private static final String MESSAGE = "존재하지 않는 레슨입니다.";

  public LessonNotFound() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
