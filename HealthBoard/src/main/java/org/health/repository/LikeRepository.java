package org.health.repository;

import org.health.domain.LikeDTO;

public interface LikeRepository {
    int addLike(LikeDTO like);
    int deleteLike(LikeDTO like);
}
