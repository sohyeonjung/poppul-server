package org.gujo.poppul.answer.service;

import org.gujo.poppul.answer.dto.AnswerStreamResponse;
import org.gujo.poppul.answer.entity.Answer;
import org.gujo.poppul.answer.repository.AnswerStreamRepository;
import org.gujo.poppul.quiz.repository.EmitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerStreamService {

    @Autowired
    private AnswerStreamRepository answerStreamRepository;
    @Autowired
    private EmitterRepository emitterRepository;

    //사용자 답안 체크
    public ResponseEntity<String> checkAnswer(String username, Long questionId, Integer answer) {
        List<Answer> answers = answerStreamRepository.findByQuestionId(questionId);
        Optional<Answer> a = answers.stream().filter(it-> it.getNo() == answer).findFirst();
        if(a.get().is_answer()){
            emitterRepository.updateScore(username, questionId);
            return ResponseEntity.ok("정답");
        }else{
            return ResponseEntity.ok("오답");
        }

    }

    //converter
    public AnswerStreamResponse toDto(Answer answer){
        return AnswerStreamResponse.builder()
                .id(answer.getId())
                .no(answer.getNo())
                .content(answer.getContent())
                .is_answer(answer.is_answer())
                .questionId(answer.getQuestion().getId())
                .build();
    }


}
