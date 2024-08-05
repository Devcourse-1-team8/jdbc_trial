package org.health.repository;

import org.health.domain.BoardDTO;

import java.util.List;

public interface BoardRepository {

    int addBoard(BoardDTO board, int user_id);
    int changeVisible(BoardDTO board);
    List<BoardDTO> findByUser_id(int user_id);
    List<BoardDTO> findAll();
    BoardDTO findById(int id);
}
