package org.health.repository;

import org.health.domain.Role;
import org.health.domain.UserDTO;
import org.health.domain.UserSignUpDTO;
import org.health.domain.UserLoginInfoDTO;
import org.health.infra.AuthManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl instance = new UserRepositoryImpl();

    private final AuthManager authManager;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;

    private UserRepositoryImpl() {
        this.authManager = AuthManager.getInstance();
    }

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
        try {
            con = DBUtil.getConnection();
            String sql = "SELECT user_id FROM user WHERE nickname = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user);

            rs = pstmt.executeQuery();
            boolean isUserExists = rs.next();
            if (!isUserExists) {
                throw new RuntimeException("User Not Found");
            }

            int userId = rs.getInt("user_id");
            authManager.login(userId);

            return userId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(rs, pstmt, con);
        }
    }

    @Override
    public void logout() {
        authManager.logout();
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> users = new ArrayList<>();
        Role myRole;

        try {
            con = DBUtil.getConnection();
            String sql = "SELECT * FROM user";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUser_id(rs.getInt("user_id"));
                user.setNickname(rs.getString("nickname"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));

                Timestamp lastLoginTimestamp = rs.getTimestamp("last_login_date");
                if (lastLoginTimestamp != null) {
                    user.setLast_login_date(lastLoginTimestamp.toLocalDateTime().toLocalDate());
                }

                user.setLogin_count(rs.getInt("login_count"));

                String role = rs.getString("role");
                if (role.equalsIgnoreCase("USER")) {
                    myRole = Role.USER;
                } else {
                    myRole = Role.MANAGER;
                }
                user.setRole(myRole);

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(rs, pstmt, con);
        }
        return users;
    }

    @Override
    public LocalDateTime findDate(int userId) {
        LocalDateTime lastLoginDate = null;
        String SQL = "SELECT last_login_date FROM user WHERE user_id = ?";
        try {
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, userId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Timestamp lastLoginTimestamp = rs.getTimestamp("last_login_date");
                if (lastLoginTimestamp != null) {
                    lastLoginDate = lastLoginTimestamp.toLocalDateTime();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(rs, pstmt, con);
        }
        return lastLoginDate;
    }


    @Override
    public int updateLoginDate(int userId, LocalDateTime date) {

        int result = 0;
        String SQL = "update user set last_login_date = ? where user_id = ?";
        try {
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(SQL);
            pstmt.setTimestamp(1, Timestamp.valueOf(date));
            pstmt.setInt(2, userId);

            result = pstmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtil.close(pstmt, con);
        }

        return result;

    }

    @Override
    public boolean isConsecutive(int userId) {
        LocalDateTime lastLoginDateTime = findDate(userId);
        if (lastLoginDateTime != null) {
            LocalDateTime now = LocalDateTime.now();
            long diff = ChronoUnit.DAYS.between(lastLoginDateTime.toLocalDate(), now.toLocalDate());
            return diff == 1;
        }
        return false;
    }

    @Override
    public boolean isConsecutiveForTest(int userId) {
        LocalDateTime lastLoginDateTime = findDate(userId);
        if (lastLoginDateTime != null) {
            LocalDateTime now = LocalDateTime.now();
            long diff = ChronoUnit.MINUTES.between(lastLoginDateTime, now);
            return diff <= 5;
        }
        return false;
    }
}