package com.artinfo.api.service;

import com.artinfo.api.exception.ArtistNotFound;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.request.concert.ConcertSearch;
import com.artinfo.api.response.concert.ArtistConcertResponse;
import com.artinfo.api.response.concert.ConcertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConcertService {

  private final ArtistRepository artistRepository;
  private final ConcertRepository concertRepository;


  public List<ConcertResponse> getList(ConcertSearch concertSearch) {
    return concertRepository.getList(concertSearch).stream()
      .map(ConcertResponse::new).toList();
  }

  public List<ArtistConcertResponse> getListByArtistId(Long artistId) {
    artistRepository.findById(artistId)
      .orElseThrow(ArtistNotFound::new);

    return concertRepository.getListByArtistId(artistId).stream()
      .map(ArtistConcertResponse::new)
      .collect(Collectors.toList());
  }
}
