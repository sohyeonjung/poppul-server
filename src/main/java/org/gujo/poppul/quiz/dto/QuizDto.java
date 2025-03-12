package org.gujo.poppul.quiz.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gujo.poppul.question.dto.QuestionDto;
import org.gujo.poppul.question.entity.Question;
import org.gujo.poppul.quiz.entity.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Long id;
    private String title;
    @NotNull(message = "UserId cannot be null")
    private String userId;
    private List<QuestionDto> questionList;

    // Entity -> DTO 변환
    public static QuizDto fromEntity(Quiz quiz) {
        List<QuestionDto> questionDtos = quiz.getQuestionList() != null ?
                quiz.getQuestionList().stream()
                        .map(QuestionDto::fromEntity)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        return QuizDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .userId(quiz.getUser_id())
                .questionList(questionDtos)
                .build();
    }

    // DTO -> Entity 변환
    public Quiz toEntity() {
        if (this.userId == null || this.userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        Quiz quiz = Quiz.builder()
                .title(this.title)
                .user_id(this.userId)
                .build();

        if (this.questionList != null) {
            List<Question> questions = this.questionList.stream()
                    .map(questionDto -> {
                        Question question = questionDto.toEntity();
                        question.setQuiz(quiz); // 양방향 관계 설정
                        return question;
                    })
                    .collect(Collectors.toList());
            quiz.setQuestionList(questions);
        }

        return quiz;
    }

}
