package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.concert.Concert;
import com.artinfo.api.request.concert.ConcertSearch;

import java.util.List;

public interface ConcertRepositoryCustom {
  List<Concert> getListByArtist(Artist artist);
  List<Concert> getList(ConcertSearch concertSearch);
}
