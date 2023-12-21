package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.concert.ConcertKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConcertKeywordRepository extends JpaRepository<ConcertKeyword, Long>, ConcertKeywordRepositoryCustom {
  Optional<ConcertKeyword> findByKeyword(String keyword);
}
