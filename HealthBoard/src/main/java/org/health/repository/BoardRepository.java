package org.health.repository;

import org.health.domain.BoardDTO;

import java.util.List;

public interface BoardRepository {

    int addBoard(BoardDTO board);
    int changeVisible(int userId);
    List<BoardDTO> findAll();
    BoardDTO findById(int id);
}
