package com.artinfo.api.controller.youtube;

import com.artinfo.api.response.youtube.ArtistYoutubeResponse;
import com.artinfo.api.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YoutubeController {

  private final YoutubeService youtubeService;

  @GetMapping("/youtubes/artist/{artistId}")
  public List<ArtistYoutubeResponse> getListByArtistId(@PathVariable(name = "artistId") Long artistId) {
    return this.youtubeService.getListByArtistId(artistId);
  }
}
