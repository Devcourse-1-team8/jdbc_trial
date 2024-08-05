package org.health.repository;

import org.health.domain.BoardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        int result = 0;

        try{
            String sql = "insert into " +
                    "board(user_id, exercise_type, exercise_time, memo, create_at,visible)" +
                    "VALUES(?,?,?,?,NOW(),TRUE)";
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, board.getUser_id());
            pstmt.setString(2, board.getExercise_type());
            pstmt.setInt(3, board.getExercise_time());
            pstmt.setString(4, board.getMemo());

            result = pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("insert board error");
        } finally {
            DBUtil.close(pstmt,con);
        }
        return result;
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
