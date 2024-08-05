package org.health.repository;

import org.health.domain.UserDTO;

import java.util.List;

public interface UserRepository {
    int addUser(UserDTO user);
    int login(UserDTO user);
    List<UserDTO> findAll();
}
