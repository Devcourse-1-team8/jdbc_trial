package org.health.repository;

import org.health.domain.BoardDTO;
import org.health.domain.BoardLikeDTO;

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
        BoardDTO boardDTO = new BoardDTO();
        List<BoardDTO> boardList = new ArrayList<>();
        String sql ="select b.board_id, u.nickname, b.exercise_type, b.create_at " +
                "from board b natural join user u " +
                "where b.visible = true and b.user_id = " + user_id;

        try {
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                boardDTO.setBoard_id(rs.getInt("b.board_id"));
                boardDTO.setUser_id(rs.getInt("b.user_id"));
                boardDTO.setNickname(rs.getString("u.nickname"));
                boardDTO.setExercise_type(rs.getString("b.exercise_type"));
                boardDTO.setExercise_time(rs.getInt("b.exercise_time"));
                boardDTO.setMemo(rs.getString("b.memo"));
                boardDTO.setCreate_at(rs.getDate("b.create_at").toLocalDate());
                boardDTO.setVisible(rs.getBoolean("b.visible"));
                boardList.add(boardDTO);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, con);
        }
        return boardList;
    }

    @Override
    public List<BoardDTO> findAll() {
        BoardDTO boardDTO = new BoardDTO();
        List<BoardDTO> list = new ArrayList<>();

        try {
            String sql ="select b.board_id, b.user_id, u.nickname, b.exercise_type, b.exercise_time, b.memo, b.create_at, b.visible " +
                    " from board b natural join user u where b.visible = true";
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
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
    public BoardLikeDTO findById(int board_id, int user_id) {
        try {
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement("select board_id, user.user_id, nickname, exercise_type, exercise_time, memo, create_at, visible " +
                    "from board " +
                    "join user on board.user_id = user.user_id " +
                    "where board_id = ?");
            pstmt.setInt(1, board_id);
            ResultSet boardInfoResultSet = pstmt.executeQuery();

            pstmt = con.prepareStatement("select count(*) as like_count " +
                    "from like_tb " +
                    "where board_id = ?");
            pstmt.setInt(1, board_id);
            ResultSet likeCountResultSet = pstmt.executeQuery();

            pstmt = con.prepareStatement("select if( " +
                    "(select count(*) from like_tb where user_id = ? and board_id = ?) = 0, " +
                    "false, " +
                    "true " +
                    ") as like_status");
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, board_id);
            ResultSet likeStatusResultSet = pstmt.executeQuery();

            return toBoardLikeDTO(boardInfoResultSet, likeCountResultSet, likeStatusResultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private BoardLikeDTO toBoardLikeDTO(ResultSet boardInfoResultSet, ResultSet likeCountResultSet, ResultSet likestatusResultSet) throws SQLException {
        boardInfoResultSet.next();
        likeCountResultSet.next();
        likestatusResultSet.next();
        BoardLikeDTO dto = new BoardLikeDTO();
        dto.setUser_id(boardInfoResultSet.getInt("user_id"));
        dto.setNickname(boardInfoResultSet.getString("nickname"));

        dto.setBoard_id(boardInfoResultSet.getInt("board_id"));
        dto.setExercise_type(boardInfoResultSet.getString("exercise_type"));
        dto.setExercise_time(boardInfoResultSet.getInt("exercise_time"));
        dto.setMemo(boardInfoResultSet.getString("memo"));
        dto.setCreate_at(boardInfoResultSet.getDate("create_at").toLocalDate());
        dto.setVisible(boardInfoResultSet.getBoolean("visible"));

        dto.setLike_count(likeCountResultSet.getInt("like_count"));
        dto.setLike_status(likestatusResultSet.getBoolean("like_status"));
        return dto;
    }

}
