package org.gujo.poppul.answer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.gujo.poppul.question.entity.Question;
import org.gujo.poppul.quiz.entity.Quiz;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "Answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int no;
    private String content;
    @Column
    private boolean is_answer;
    @ManyToOne()
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "question_id")
    private Question question;
}