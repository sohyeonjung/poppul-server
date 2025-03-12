package org.gujo.poppul.quiz.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepository {

    //SseEmitter 객체 관리
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    //quiz_id별로 해당하는 username을 저장
    private final Map<Long, List<String>> info = new ConcurrentHashMap<>();
    //pin저장 username-pin
    private final Map<String, Integer> pins = new ConcurrentHashMap<>();
    //user별 score 저장 quizid-username-scoresum
    private final Map<Long, Map<String, Integer>> scores = new ConcurrentHashMap<>();


    //퀴즈 접속자 이름으로 boardcast를 위해 구독 중인 사용자의 SseEmitter 조회
    public SseEmitter findByName(String username) {
        return emitters.get(username);
    }

    //quizid별 user 구하기
    public Collection<SseEmitter> findByQuizId(Long quizId) {
        List<String> usernames = info.get(quizId);
        if (usernames != null) {
            return usernames.stream()
                    .map(emitters::get)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    //접속사 저장
    public SseEmitter save(Long quizId, String username, Integer pin, SseEmitter emitter) {
        emitters.put(username, emitter);
        info.computeIfAbsent(quizId, k -> new ArrayList<>()).add(username);
        pins.put(username, pin);
        return emitters.get(username);
    }

    //접속자 삭제
    public void deleteByName(String username) {
        emitters.remove(username);
        info.forEach((quizId, usernames) -> usernames.remove(username));
        pins.remove(username);
    }

    //전체 sseemitter 가져오기
    public Collection<SseEmitter> values() {
        return emitters.values();
    }

    //username 반환
    public String findUsernameByEmitter(SseEmitter emitter) {
        return emitters.entrySet()
                .stream()
                .filter(entry->entry.getValue().equals(emitter))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public void updateScore(String username, Long quizId){
        Map<String, Integer> orscore = scores.computeIfAbsent(quizId, k -> new ConcurrentHashMap<>());

        orscore.merge(username, 10, Integer::sum);
    }

    public List<Map.Entry<String, Integer>> getRankedScores(Long quizId) {
        // Integer 값을 기준으로 오름차순 정렬
        Map<String, Integer> scoresForQuiz = scores.get(quizId);

        if (scoresForQuiz == null) {
            // 해당 퀴즈에 점수 기록이 없다면 빈 리스트 반환
            return Collections.emptyList();
        }

        // Integer 값을 기준으로 오름차순 정렬
        return scoresForQuiz.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // 내림차순 정렬 (점수가 높은 순으로)
                .collect(Collectors.toList());
    }

    @Transactional
    public void clearByQuizId(Long quizId) {

        List<String> users = info.get(quizId);

        // 사용자 목록이 존재한다면 해당 사용자들을 삭제합니다.
        if (users != null) {
            // SseEmitter에서 해당 사용자 제거
            users.forEach(username -> {
                SseEmitter emitter = emitters.remove(username);
                if (emitter != null) {
                    emitter.complete();  // Ensure the SSE connection is properly closed
                }
            });

            // pins에서 해당 사용자들에 대한 PIN을 제거
            users.forEach(username -> pins.remove(username));

            // 사용자들 정보를 삭제합니다.
            info.remove(quizId);

            // 점수 정보 삭제
            scores.remove(quizId);
        }
    }


//
//    // 특정 유저의 PIN 가져오기
//    public Integer getPin(String username) {
//        return pins.get(username);
//    }
//

//        public SseEmitter findByUsername(String username, Long quizId) {return emitters.get(username);}

    //특정 유저인지 찾기
//    public boolean findByUsername(String username) {return emitters.containsKey(username);}



}
