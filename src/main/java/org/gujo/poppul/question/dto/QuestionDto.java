package org.gujo.poppul.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gujo.poppul.answer.dto.AnswerDto;
import org.gujo.poppul.answer.entity.Answer;
import org.gujo.poppul.question.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    private String title;
    private String image;
    private List<AnswerDto> answerList;

    // Entity -> DTO 변환
    public static QuestionDto fromEntity(Question question) {
        List<AnswerDto> answerDtos = question.getAnswerList() != null ?
                question.getAnswerList().stream()
                        .map(AnswerDto::fromEntity)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        return QuestionDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .image(question.getImage())
                .answerList(answerDtos)
                .build();
    }

    // DTO -> Entity 변환
    public Question toEntity() {
        Question question = Question.builder()
                .title(this.title)
                .image(this.image)
                .build();

        if (this.answerList != null) {
            List<Answer> answers = this.answerList.stream()
                    .map(answerDto -> {
                        Answer answer = answerDto.toEntity();
                        answer.setQuestion(question); // 양방향 관계 설정
                        return answer;
                    })
                    .collect(Collectors.toList());
            question.setAnswerList(answers);
        }

        return question;
    }
}