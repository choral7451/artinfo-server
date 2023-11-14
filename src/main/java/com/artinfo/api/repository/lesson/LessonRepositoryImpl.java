package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.Lesson;
import com.artinfo.api.request.LessonSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.artinfo.api.domain.QLesson.*;

@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonsRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Lesson> getList(LessonSearch lessonSearch) {
    BooleanExpression locationCondition = null;
    if (lessonSearch.getLocation() != null && !lessonSearch.getLocation().isEmpty()) {
      for (String location : lessonSearch.getLocation()) {
        BooleanExpression currentCondition = Expressions.booleanTemplate(
          "CAST({0} AS text) like {1}",
          lesson.locations,
          "%" + location + "%"
        );
        locationCondition = locationCondition == null ? currentCondition : locationCondition.or(currentCondition);
      }
    }

    BooleanExpression subjectCondition = null;
    if (lessonSearch.getSubject() != null && !lessonSearch.getSubject().isEmpty()) {
      for (String subject : lessonSearch.getSubject()) {
        BooleanExpression currentCondition = Expressions.booleanTemplate(
          "CAST({0} AS text) like {1}",
          lesson.subjects,
          "%" + subject + "%"
        );
        subjectCondition = subjectCondition == null ? currentCondition : subjectCondition.or(currentCondition);
      }
    }

    JPAQuery<Lesson> query = jpaQueryFactory.selectFrom(lesson);

    if (locationCondition != null && subjectCondition != null) {
      query.where(locationCondition.and(subjectCondition));
    } else if (locationCondition != null) {
      query.where(locationCondition);
    } else if (subjectCondition != null) {
      query.where(subjectCondition);
    }

    return query.limit(lessonSearch.getSize())
      .offset(lessonSearch.getOffset())
      .orderBy(lesson.id.desc())
      .fetch();
  }
}
