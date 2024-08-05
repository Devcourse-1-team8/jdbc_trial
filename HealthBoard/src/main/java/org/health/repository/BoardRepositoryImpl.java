package org.health.repository;

import org.health.domain.BoardDTO;

import java.sql.*;
import java.util.ArrayList;
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
        int result = 0;

        try {
            String sql = "update board set visible = false where board_id = "
                    + board.getBoard_id();
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(pstmt, con);
        }
        return result;
    }

    @Override
    public List<BoardDTO> findByUser_id(int user_id) {
        return null;
    }

    @Override
    public List<BoardDTO> findAll() {
        List<BoardDTO> list = new ArrayList<>();

        try {
            String sql ="select b.board_id, b.user_id, u.nickname, b.exercise_type, b.exercise_time, b.memo, b.create_at, b.visible " +
                    " from board b natural join user u where b.visible = true";
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                BoardDTO boardDTO = new BoardDTO();

                boardDTO.setBoard_id(rs.getInt("b.board_id")); //int
                boardDTO.setUser_id(rs.getInt("b.user_id"));  //int
                boardDTO.setNickname(rs.getString("u.nickname"));
                boardDTO.setExercise_type(rs.getString("b.exercise_type"));
                boardDTO.setExercise_time(rs.getInt("b.exercise_time"));    //int
                boardDTO.setMemo(rs.getString("b.memo"));
                Date sqlDate = rs.getDate("b.create_at");
                boardDTO.setCreate_at(sqlDate.toLocalDate());
                boardDTO.setVisible(rs.getBoolean("b.visible"));  //boolean

                list.add(boardDTO);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(rs, pstmt, con);
        }
        return list;
    }

    @Override
    public BoardDTO findById(int id) {
        return null;
    }
}
