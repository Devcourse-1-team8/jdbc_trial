package org.health.repository;

import org.health.domain.LikeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeRepositoryImpl implements LikeRepository {
    private static LikeRepositoryImpl instance = new LikeRepositoryImpl();

    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;

    private LikeRepositoryImpl() {
    }

    public static LikeRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public int addLike(LikeDTO like) {
        int result = 0;

        try {
            con = DBUtil.getConnection();
            //이미 좋아요했는지 체크
            String search = "select * from like_tb where user_id = ? AND board_id = ?";
            pstmt = con.prepareStatement(search);
            pstmt.setInt(1, like.getUser_id());
            pstmt.setInt(2, like.getBoard_id());
            rs = pstmt.executeQuery();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }
            if (rowCount > 0) {
                System.out.println("이미 좋아요를 했습니다.");
                return -1;
            }

            String sql = "insert into " +
                    "like_tb(user_id, board_id) " +
                    "VALUES(?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, like.getUser_id());
            pstmt.setInt(2, like.getBoard_id());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert like error");
            System.out.println(e.getMessage());
        } finally {
            DBUtil.close(pstmt, con, rs);
        }
        return result;
    }

    @Override
    public int deleteLike(LikeDTO like) {
        int result = 0;

        try {
            con = DBUtil.getConnection();
            // 이미 좋아요가 있는지 체크
            String search = "SELECT * FROM like_tb WHERE user_id = ? AND board_id = ?";
            pstmt = con.prepareStatement(search);
            pstmt.setInt(1, like.getUser_id());
            pstmt.setInt(2, like.getBoard_id());
            rs = pstmt.executeQuery();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }
            if (rowCount == 0) {
                System.out.println("좋아요를 하지 않았습니다.");
                return -1;
            }

            // 기존 pstmt를 닫고 새로운 쿼리 준비
            rs.close();
            pstmt.close();

            // 좋아요 삭제
            String sql = "DELETE FROM like_tb WHERE like_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, like.getLike_id());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete like error");
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, con);
        }
        return result;
    }
}
