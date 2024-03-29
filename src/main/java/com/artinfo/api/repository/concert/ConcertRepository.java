package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {
}
