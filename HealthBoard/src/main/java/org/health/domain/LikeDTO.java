package org.health.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LikeDTO {
    private int like_id;
    private int user_id;
    private int board_id;
}
