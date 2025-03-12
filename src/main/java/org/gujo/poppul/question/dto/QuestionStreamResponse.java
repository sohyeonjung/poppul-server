package org.gujo.poppul.question.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.gujo.poppul.answer.dto.AnswerStreamResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuestionStreamResponse {

    private Long id;
    private String title;
    private String image;
    private Long quizId;

    private List<AnswerStreamResponse> answerList = new ArrayList<>();
}
