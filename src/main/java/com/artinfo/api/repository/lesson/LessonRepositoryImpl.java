package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.request.lesson.LessonSearch;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.artinfo.api.domain.lesson.QLesson.lesson;

@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonsRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Lesson> getList(LessonSearch lessonSearch) {
    List<Long> ids = findLessonIds(lessonSearch);

    return findLessonsByIds(ids);
  }

  private List<Long> findLessonIds(LessonSearch lessonSearch) {
    JPAQuery<Long> jpaQuery = jpaQueryFactory.select(lesson.id)
      .from(lesson)
      .limit(lessonSearch.getSize())
      .offset(lessonSearch.getOffset())
      .orderBy(lesson.createdAt.desc());

    if (lessonSearch.getMajor() != null && !lessonSearch.getMajor().isEmpty()) {
      jpaQuery = jpaQuery.where(lesson.majors.any().name.in(lessonSearch.getMajor()));
    }

    if (lessonSearch.getLocation() != null && !lessonSearch.getLocation().isEmpty()) {
      jpaQuery = jpaQuery.where(lesson.locations.any().name.in(lessonSearch.getLocation()));
    }

    return jpaQuery.fetch();
  }

  private List<Lesson> findLessonsByIds(List<Long> ids) {
    return jpaQueryFactory.selectFrom(lesson)
      .leftJoin(lesson.majors)
      .fetchJoin()
      .leftJoin(lesson.locations)
      .fetchJoin()
      .where(lesson.id.in(ids))
      .orderBy(lesson.createdAt.desc())
      .fetch();
  }
}
