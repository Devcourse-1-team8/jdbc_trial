package org.health.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginInfoDTO {
    private int userId;
    private LocalDateTime loginTime;
}
