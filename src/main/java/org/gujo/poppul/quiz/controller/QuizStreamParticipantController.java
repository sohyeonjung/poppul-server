//package org.gujo.poppul.quiz.controller;
//
//import org.gujo.poppul.quiz.entity.ParticipantManager;
//import org.gujo.poppul.quiz.service.QuizStreamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/quiz")
//public class ParticipantController {
//
//    @Autowired
//    private final QuizStreamService quizService;
//    @Autowired
//    private final ParticipantManager participantManager;
//
//    //참가자가 퀴즈에 입장 (PIN 번호 입력)
//    @PostMapping("/join")
//    public ResponseEntity<String> joinQuiz(@RequestParam String pinCode, @RequestParam String username) {
//        if (!quizService.isValidPin(pinCode)) {
//            return ResponseEntity.badRequest().body("잘못된 PIN 번호입니다.");
//        }
//        participantManager.addParticipant(username);
//        return ResponseEntity.ok("참가 성공!");
//    }
//
//    // 🔹 참가자가 답변 제출
//    @PostMapping("/answer")
//    public ResponseEntity<String> submitAnswer(@RequestParam String username, @RequestParam Long answerId) {
//        boolean isCorrect = quizService.checkAnswer(answerId);
//        if (isCorrect) {
//            participantManager.updateScore(username, 10); // 정답 시 10점 추가
//            return ResponseEntity.ok("정답! +10점");
//        }
//        return ResponseEntity.ok("오답!");
//    }
//}
//
