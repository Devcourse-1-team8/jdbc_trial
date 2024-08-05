package org.health.repository;

import org.health.domain.Role;
import org.health.domain.UserDTO;
import org.health.domain.UserSignUpDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepositoryImpl instance = new UserRepositoryImpl();

    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;

    private UserRepositoryImpl() {}

    public static UserRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public int addUser(UserSignUpDTO user) {
        try {
            con = DBUtil.getConnection();
            String sql = "INSERT INTO user (nickname, age, gender, role) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getNickname());
            pstmt.setInt(2, user.getAge());
            pstmt.setString(3, user.getGender());
            pstmt.setString(4, String.valueOf(Role.USER));

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(pstmt, con);
        }
    }

    @Override
    public int login(String user) {
        return 0;
    }

    @Override
    public void logout() {}

    @Override
    public List<UserDTO> findAll() {
        return null;
    }
}
