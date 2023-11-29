package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.Concert;

import java.util.List;

public interface ConcertRepositoryCustom {
  List<Concert> getListByArtistId(Long artistId);
}
