package org.gujo.poppul.quiz.entity;
import jakarta.persistence.*;
import lombok.*;
import org.gujo.poppul.question.entity.Question;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 퀴즈 제목

    @Column(name = "user_id")  // DB 컬럼명 그대로 유지
    @Access(AccessType.FIELD) // 필드 직접 접근 허용 (Spring JPA가 user_id 그대로 인식하도록 함)
    private String user_id;

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questionList = new ArrayList<>();
}