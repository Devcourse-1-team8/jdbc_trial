package org.health.repository;

import org.health.domain.UserDTO;
import org.health.domain.UserSignUpDTO;

import java.util.List;

public interface UserRepository {
    int addUser(UserSignUpDTO user);
    int login(String user);
    void logout();
    List<UserDTO> findAll();
}
