package org.gujo.poppul.answer.repository;


import org.gujo.poppul.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnswerStreamRepository extends JpaRepository<Answer, Long> {
    //TODO
    //Optional<Answer> findByQuestionIdAndIsAnswerTrue(Long questionId);
    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId AND a.is_answer = true")
    Optional<Answer> findByQuestionIdAndIsAnswerTrue(Long questionId);


    List<Answer> findByQuestionId(Long questionId);


}
