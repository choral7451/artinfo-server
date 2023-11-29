package com.artinfo.api.controller.concert;

import com.artinfo.api.response.concert.ArtistConcertResponse;
import com.artinfo.api.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConcertController {

  private final ConcertService concertService;

  @GetMapping("/concerts/artist/{artistId}")
  public List<ArtistConcertResponse> getListByArtistId(@PathVariable(name = "artistId") Long artistId) {
    return this.concertService.getListByArtistId(artistId);
  }
}
