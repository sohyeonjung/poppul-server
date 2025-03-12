package org.gujo.poppul.quiz.repository;

import org.gujo.poppul.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // JPQL을 사용한 명시적 쿼리 정의
    @Query("SELECT q FROM Quiz q WHERE q.user_id = :userId")
    List<Quiz> findByUserId(@Param("userId") String userId);
}