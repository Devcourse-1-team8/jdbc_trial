package org.health.repository;

import org.health.domain.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public List<UserDTO> findAll() {
        return null;
    }
}
