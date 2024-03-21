package com.artinfo.api.exception;

public class ConcertNotFound extends ArtinfoException {
  private static final String MESSAGE = "존재하지 않는 공연입니다.";

  public ConcertNotFound() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
