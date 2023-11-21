package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

  Optional<Location> findByName(String name);
}
