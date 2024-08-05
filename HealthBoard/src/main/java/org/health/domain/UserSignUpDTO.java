package org.health.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpDTO {
    private String nickname;
    private String gender;
    private int age;
}
