package org.health.repository;

import org.health.domain.Role;
import org.health.domain.UserDTO;
import org.health.domain.UserLoginInfoDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    public int addUser(UserDTO user) {
        return 0;
    }

    @Override
    public int login(String user) {
        return 0;
    }

    @Override
    public void logout() {}

    @Override
    public List<UserDTO> findAll() throws SQLException {
        List<UserDTO> users = new ArrayList<>();
        DBUtil dbUtil = new DBUtil();
        Role myRole;
        con = dbUtil.getConnection();
        String SQL = "select * from user";
        pstmt = con.prepareStatement(SQL);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            int user_id = rs.getInt(1);
            String nickname = rs.getString(2);
            int age = rs.getInt(3);
            String gender = rs.getString(4);
            LocalDate last_login_date = rs.getDate(5).toLocalDate();
            int login_count = rs.getInt(6);
            String role = rs.getString(7);

            UserDTO user = new UserDTO();
            user.setUser_id(user_id);
            user.setNickname(nickname);
            user.setAge(age);
            user.setGender(gender);
            user.setLast_login_date(last_login_date);
            user.setLogin_count(login_count);
            if (role.equals("user")) {
                myRole = Role.user;
            }
            else {
                myRole = Role.manager;
            }
            user.setRole(myRole);
            users.add(user);
        }
        return users;
    }
  
    @Override
    public UserLoginInfoDTO findUserIdAndDate(int userId) {
        UserLoginInfoDTO userLoginInfoDTO = null;
        String SQL = "select user_id, last_login_date from user where user_id = ?";
        try {
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, userId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                userLoginInfoDTO = new UserLoginInfoDTO();
                userLoginInfoDTO.setUserId(rs.getInt("user_id"));
                userLoginInfoDTO.setLoginTime(rs.getTimestamp("last_login_date").toLocalDateTime());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userLoginInfoDTO;
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
        }

        return result;

    }

    @Override
    public boolean isConsecutive(int userId) {
        UserLoginInfoDTO userLoginInfo = findUserIdAndDate(userId);
        if (userLoginInfo != null && userLoginInfo.getLoginTime() != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastLoginDateTime = userLoginInfo.getLoginTime();
            long diff = ChronoUnit.DAYS.between(lastLoginDateTime.toLocalDate(), now.toLocalDate());
            return diff == 1;
        }
        return false;
    }

    @Override
    public boolean isConsecutiveForTest(int userId) {
        UserLoginInfoDTO userLoginInfo = findUserIdAndDate(userId);
        if (userLoginInfo != null && userLoginInfo.getLoginTime() != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastLoginDateTime = userLoginInfo.getLoginTime();
            long diff = ChronoUnit.MINUTES.between(lastLoginDateTime, now);
            return diff <= 5;
        }
        return false;
    }
}