package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.Concert;
import com.artinfo.api.request.concert.ConcertSearch;

import java.util.List;

public interface ConcertRepositoryCustom {
  List<Concert> getListByArtistId(Long artistId);
  List<Concert> getList(ConcertSearch concertSearch);
}
