package com.artinfo.api.service;

import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.response.concert.ArtistConcertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConcertService {

  private final ConcertRepository concertRepository;

  public List<ArtistConcertResponse> getListByArtistId(Long artistId) {
    return concertRepository.getListByArtistId(artistId).stream()
      .map(ArtistConcertResponse::new)
      .collect(Collectors.toList());
  }
}
