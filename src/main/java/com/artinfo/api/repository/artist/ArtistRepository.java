package com.artinfo.api.repository.artist;

import com.artinfo.api.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository  extends JpaRepository<Artist, Long>, ArtistRepositoryCustom {
}
