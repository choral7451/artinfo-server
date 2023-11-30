package com.artinfo.api.repository.image;

import com.artinfo.api.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
