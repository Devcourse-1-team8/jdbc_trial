package org.health.repository;

import org.health.domain.LikeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LikeRepositoryImpl implements LikeRepository {
    private static LikeRepositoryImpl instance = new LikeRepositoryImpl();

    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;

    private LikeRepositoryImpl() {}

    public static LikeRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public int addLike(LikeDTO like) {
        return 0;
    }

    @Override
    public int deleteLike(int id) {
        return 0;
    }
}
