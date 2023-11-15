package com.artinfo.api.exception;

public class ProfileNotFound extends ArtinfoException {
  private static final String MESSAGE = "존재하지 않는 프로필입니다.";

  public ProfileNotFound() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
