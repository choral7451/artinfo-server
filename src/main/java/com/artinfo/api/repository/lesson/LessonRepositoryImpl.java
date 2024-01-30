package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.request.lesson.LessonSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    BooleanBuilder whereClause = new BooleanBuilder();

    if (lessonSearch.getMajor() != null && !lessonSearch.getMajor().isEmpty()) {
      BooleanExpression majorCondition = lesson.majors.any().name.containsIgnoreCase(lessonSearch.getMajor().get(0));
      for (int i = 1; i < lessonSearch.getMajor().size(); i++) {
        majorCondition = majorCondition.or(lesson.majors.any().name.containsIgnoreCase(lessonSearch.getMajor().get(i)));
      }
      whereClause.and(majorCondition);
    }

    if (lessonSearch.getLocation() != null && !lessonSearch.getLocation().isEmpty()) {
      BooleanExpression locationCondition = lesson.locations.any().name.containsIgnoreCase(lessonSearch.getLocation().get(0));
      for (int i = 1; i < lessonSearch.getLocation().size(); i++) {
        locationCondition = locationCondition.or(lesson.locations.any().name.containsIgnoreCase(lessonSearch.getLocation().get(i)));
      }
      whereClause.and(locationCondition);
    }

    jpaQuery = jpaQuery.where(whereClause);

    return jpaQuery.fetch();
  }

  private List<Lesson> findLessonsByIds(List<Long> ids) {
    return jpaQueryFactory.selectFrom(lesson)
      .leftJoin(lesson.majors)
      .rightJoin(lesson.locations)
      .fetchJoin()
      .where(lesson.id.in(ids))
      .orderBy(lesson.createdAt.desc())
      .fetch();
  }
}
