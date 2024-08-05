package org.health.repository;

import org.health.domain.LikeDTO;

import java.time.LocalDate;

public interface LikeRepository {
    int addLike(LikeDTO like);
    int deleteLike(LikeDTO like);

  
}
