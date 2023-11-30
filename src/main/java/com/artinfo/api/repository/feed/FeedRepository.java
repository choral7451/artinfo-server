package com.artinfo.api.repository.feed;

import com.artinfo.api.domain.Feed;
import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.repository.lesson.LessonsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
