package com.artinfo.api.exception;

public class ArtistNotFound extends ArtinfoException {
  private static final String MESSAGE = "존재하지 않는 아티스트입니다.";

  public ArtistNotFound() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
