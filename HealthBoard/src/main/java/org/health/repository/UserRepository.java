package org.health.repository;

import org.health.domain.UserDTO;
import org.health.domain.UserSignUpDTO;
import org.health.domain.UserLoginInfoDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository {
    int addUser(UserSignUpDTO user);
    int login(String user);
    void logout();
  
    List<UserDTO> findAll() throws SQLException;
    UserLoginInfoDTO findUserIdAndDate(int userId);
    int updateLoginDate(int userId, LocalDateTime date);

    boolean isConsecutive(int userId);

    boolean isConsecutiveForTest(int userId);
}
