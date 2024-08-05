package org.health.repository;

import org.health.domain.BoardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class BoardRepositoryImpl implements BoardRepository {
    private static BoardRepositoryImpl instance = new BoardRepositoryImpl();

    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;

    private BoardRepositoryImpl() {}

    public static BoardRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public int addBoard(BoardDTO board) {
        return 0;
    }

    @Override
    public int changeVisible(BoardDTO board) {
        return 0;
    }

    @Override
    public List<BoardDTO> findByUser_id(int user_id) {
        return null;
    }

    @Override
    public List<BoardDTO> findAll() {
        return null;
    }

    @Override
    public BoardDTO findById(int id) {
        return null;
    }
}
