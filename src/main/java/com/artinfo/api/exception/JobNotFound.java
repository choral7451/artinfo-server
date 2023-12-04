package com.artinfo.api.exception;

public class JobNotFound extends ArtinfoException {
  private static final String MESSAGE = "존재하지 않는 채용입니다.";

  public JobNotFound() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
