package org.gujo.poppul.answer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;
import org.gujo.poppul.question.entity.Question;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AnswerStreamResponse {
    private Long id;
    private int no;
    private String content;
    private boolean is_answer;
    private Long questionId;
}
