package org.gujo.poppul.user.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String id;
    private String password;
    private String name;
}