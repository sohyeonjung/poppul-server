package org.gujo.poppul.quiz.repository;

import org.gujo.poppul.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface QuizStreamRepository extends JpaRepository<Quiz, Long> {
    //LAzayException대비 fetch join
    @Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.questionList WHERE q.id = :quizId")
    Optional<Quiz> findQuizWithQuestions(@Param("quizId") Long quizId);
}
