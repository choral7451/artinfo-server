package com.artinfo.api.repository.feed;

import com.artinfo.api.domain.Feed;
import com.artinfo.api.request.feed.FeedSearch;

import java.util.List;

public interface FeedRepositoryCustom {
  List<Feed> getList(FeedSearch feedSearch);
}
