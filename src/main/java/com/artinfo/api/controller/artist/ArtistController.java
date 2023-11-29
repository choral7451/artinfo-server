package com.artinfo.api.controller.artist;

import com.artinfo.api.request.artist.ArtistSearch;
import com.artinfo.api.response.artist.ArtistResponse;
import com.artinfo.api.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtistController {

  private final ArtistService artistService;

  @GetMapping("/artists")
  public List<ArtistResponse> getList(@ModelAttribute ArtistSearch artistSearch) {
    return this.artistService.getList(artistSearch);
  }
}
