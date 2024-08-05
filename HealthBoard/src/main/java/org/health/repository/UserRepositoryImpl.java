package org.health.repository;

import org.health.domain.Role;
import org.health.domain.UserDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
            System.out.println(user);
            users.add(user);
        }

        // 추후 삭제하기
        System.out.println("UserRepositoryImpl.find all");
        return users;
    }
}