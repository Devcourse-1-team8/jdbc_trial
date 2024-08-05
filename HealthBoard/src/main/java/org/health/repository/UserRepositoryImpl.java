package org.health.repository;

import org.health.domain.UserDTO;

import java.util.List;

public class UserRepositoryImpl implements UserRepository{
    @Override
    public int addUser(UserDTO user) {
        return 0;
    }

    @Override
    public int login(UserDTO user) {
        return 0;
    }

    @Override
    public List<UserDTO> findAll() {
        return null;
    }
}
