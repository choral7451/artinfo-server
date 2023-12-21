package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.concert.ConcertKeyword;

import java.util.List;

public interface ConcertKeywordRepositoryCustom {
  List<ConcertKeyword> findTopNByOrderByFetchCountDesc(Integer size);
}
