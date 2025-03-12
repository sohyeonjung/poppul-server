package org.gujo.poppul.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gujo.poppul.quiz.entity.Quiz;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 255)
    private String password;
    
    @OneToMany
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private List<Quiz> quizzes = new ArrayList<>();
}