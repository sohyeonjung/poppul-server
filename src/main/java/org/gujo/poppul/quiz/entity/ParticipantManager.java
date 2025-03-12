package org.gujo.poppul.quiz.entity;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ParticipantManager {
    private final Map<String, Integer> participants = new HashMap<>();

    public void addParticipant(String name) {
        participants.put(name, 0);
    }

    public void updateScore(String name, int score) {
        participants.put(name, participants.getOrDefault(name, 0) + score);
    }

    public Map<String, Integer> getLeaderboard() {
        return participants.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue()) // 점수 내림차순 정렬
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        //Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
