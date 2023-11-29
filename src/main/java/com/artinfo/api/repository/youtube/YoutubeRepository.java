package com.artinfo.api.repository.youtube;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Youtube;
import com.artinfo.api.repository.artist.ArtistRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeRepository extends JpaRepository<Youtube, Long>, YoutubeRepositoryCustom {
}
