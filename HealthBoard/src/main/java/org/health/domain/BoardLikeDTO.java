package org.health.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardLikeDTO {
    private int board_id;
    private int user_id;
    private String nickname;
    private String exercise_type;
    private int exercise_time;
    private String memo;
    private LocalDate create_at;
    private boolean visible;
    private int like_count;
    private boolean like_status;
}
