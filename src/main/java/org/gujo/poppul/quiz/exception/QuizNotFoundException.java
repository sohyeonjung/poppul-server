package org.gujo.poppul.quiz.exception;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(String message) {
        super(message); // 부모 클래스의 생성자 호출하여 메시지 전달
    }
}
