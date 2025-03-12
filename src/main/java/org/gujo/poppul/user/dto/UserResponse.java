package org.gujo.poppul.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String name;
    private boolean success; // 필드 이름 명확히 'success'로 정의
    private String message;
}