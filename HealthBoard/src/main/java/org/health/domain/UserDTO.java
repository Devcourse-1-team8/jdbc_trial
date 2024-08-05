package org.health.domain;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private int user_id;
    private String nickname;
    private int age;
    private String gender;
    private LocalDate last_login_date;
    private int login_count;
    private Role role;
}
