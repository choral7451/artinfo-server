package com.artinfo.api.controller.concert;

import com.artinfo.api.request.concert.ConcertCreate;
import com.artinfo.api.request.concert.ConcertKeywordSearch;
import com.artinfo.api.request.concert.ConcertSearch;
import com.artinfo.api.response.concert.ArtistConcertResponse;
import com.artinfo.api.response.concert.ConcertDetailResponse;
import com.artinfo.api.response.concert.ConcertResponse;
import com.artinfo.api.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConcertController {

  private final ConcertService concertService;

  @GetMapping("/concerts/{concertId}")
  public ConcertDetailResponse get(@PathVariable(name = "concertId") Long concertId) {
    return concertService.get(concertId);
  }

  @PostMapping("/concerts")
  public void create(@RequestBody ConcertCreate concertCreate) {
    this.concertService.create(concertCreate);
  }

  @GetMapping("/concerts/artist/{artistId}")
  public List<ArtistConcertResponse> getListByArtistId(@PathVariable(name = "artistId") Long artistId) {
    return this.concertService.getListByArtistId(artistId);
  }

  @GetMapping("/concerts")
  public List<ConcertResponse> getList(@ModelAttribute ConcertSearch concertSearch) {
    return this.concertService.getList(concertSearch);
  }

  @GetMapping("/concerts/keywords")
  public List<String> getKeywordList(@ModelAttribute ConcertKeywordSearch concertKeywordSearch) {
    return this.concertService.getKeywordList(concertKeywordSearch);
  }
}
