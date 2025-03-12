package org.gujo.poppul.quiz.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.gujo.poppul.question.dto.QuestionStreamResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuizStreamResponse {
    private Long id;
    private String title;
    private List<QuestionStreamResponse> questionList = List.of();

}
